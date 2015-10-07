/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.impl.engine.ws

import java.util.Random

import scala.concurrent.duration._

import akka.http.ClientConnectionSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.{ ProductVersion, `User-Agent` }
import akka.http.scaladsl.model.ws._
import akka.http.scaladsl.model.Uri
import akka.stream.io._
import akka.stream.scaladsl._
import akka.stream.testkit.{ TestSubscriber, TestPublisher }
import akka.util.ByteString
import org.scalatest.{ Matchers, FreeSpec }

import akka.http.impl.util._

class WebsocketClientSpec extends FreeSpec with Matchers with WithMaterializerSpec {
  "The client-side Websocket implementation should" - {
    "establish a websocket connection when the user requests it" in new EstablishedConnectionSetup with ClientEchoes
    "reject invalid handshakes" - {
      "missing Sec-WebSocket-Accept hash" in pending
      "wrong Sec-WebSocket-Accept hash" in pending
      "missing `Upgrade` header" in pending
      "missing `Connection: upgrade` header" in pending
    }

    "don't send out frames before handshake was finished successfully" in new TestSetup {
      def clientImplementation: Flow[Message, Message, Unit] =
        Flow.wrap(Sink.ignore, Source.single(TextMessage("fast message")))(Keep.none)

      expectWireData(UpgradeRequestBytes)
      expectNoWireData()

      sendWireData(UpgradeResponseBytes)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("fast message"), fin = true)

      expectMaskedCloseFrame(Protocol.CloseCodes.Regular)
      sendWSCloseFrame(Protocol.CloseCodes.Regular)

      closeNetworkInput()
      expectNetworkClose()
    }
    "receive first frame in same chunk as HTTP upgrade response" in new TestSetup with ClientProbes {
      expectWireData(UpgradeRequestBytes)

      val firstFrame = WSTestUtils.frame(Protocol.Opcode.Text, ByteString("fast"), fin = true, mask = false)
      sendWireData(UpgradeResponseBytes ++ firstFrame)

      messagesIn.requestNext(TextMessage("fast"))
    }

    "manual scenario client sends first" in new EstablishedConnectionSetup with ClientProbes {
      messagesOut.sendNext(TextMessage("Message 1"))

      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 1"), fin = true)

      sendWSFrame(Protocol.Opcode.Binary, ByteString("Response"), fin = true, mask = false)

      messagesIn.requestNext(BinaryMessage(ByteString("Response")))
    }
    "client echoes scenario" in new EstablishedConnectionSetup with ClientEchoes {
      sendWSFrame(Protocol.Opcode.Text, ByteString("Message 1"), fin = true)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 1"), fin = true)
      sendWSFrame(Protocol.Opcode.Text, ByteString("Message 2"), fin = true)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 2"), fin = true)
      sendWSFrame(Protocol.Opcode.Text, ByteString("Message 3"), fin = true)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 3"), fin = true)
      sendWSFrame(Protocol.Opcode.Text, ByteString("Message 4"), fin = true)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 4"), fin = true)
      sendWSFrame(Protocol.Opcode.Text, ByteString("Message 5"), fin = true)
      expectMaskedFrameOnNetwork(Protocol.Opcode.Text, ByteString("Message 5"), fin = true)

      sendWSCloseFrame(Protocol.CloseCodes.Regular)
      expectMaskedCloseFrame(Protocol.CloseCodes.Regular)

      closeNetworkInput()
      expectNetworkClose()
    }
  }

  def UpgradeRequestBytes = ByteString {
    """GET /ws HTTP/1.1
      |Upgrade: websocket
      |Connection: upgrade
      |Sec-WebSocket-Key: YLQguzhR2dR6y5M9vnA5mw==
      |Sec-WebSocket-Version: 13
      |Host: example.org
      |User-Agent: akka-http/test
      |
      |""".stripMarginWithNewline("\r\n")
  }

  def UpgradeResponseBytes = ByteString {
    """HTTP/1.1 101 Switching Protocols
      |Upgrade: websocket
      |Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
      |Server: akka-http/test
      |Connection: upgrade
      |
      |""".stripMarginWithNewline("\r\n")
  }

  abstract class EstablishedConnectionSetup extends TestSetup {
    expectWireData(UpgradeRequestBytes)
    sendWireData(UpgradeResponseBytes)
  }

  abstract class TestSetup extends WSTestSetupBase {
    protected def noMsgTimeout: FiniteDuration = 100.millis
    protected def clientImplementation: Flow[Message, Message, Unit]

    val random = new Random(0)
    def settings = ClientConnectionSettings(system)
      .copy(userAgentHeader = Some(`User-Agent`(List(ProductVersion("akka-http", "test")))))

    def targetUri: Uri = "ws://example.org/ws"

    def clientLayer: Http.WebsocketClientLayer =
      Http(system).websocketClientLayer(targetUri, settings, random)

    val (netOut, netIn) = {
      val netOut = ByteStringSinkProbe()
      val netIn = TestPublisher.probe[ByteString]()

      FlowGraph.closed(clientLayer) { implicit b ⇒
        client ⇒
          import FlowGraph.Implicits._
          Source(netIn) ~> Flow[ByteString].map(SessionBytes(null, _)) ~> client.in2
          client.out1 ~> Flow[SslTlsOutbound].collect { case SendBytes(x) ⇒ x } ~> netOut.sink
          client.out2 ~> clientImplementation ~> client.in1
      }.run()

      netOut -> netIn
    }
    def expectBytes(length: Int): ByteString = netOut.expectBytes(length)
    def expectBytes(bytes: ByteString): Unit = netOut.expectBytes(bytes)

    def wipeDate(string: String) =
      string.fastSplit('\n').map {
        case s if s.startsWith("Date:") ⇒ "Date: XXXX\r"
        case s                          ⇒ s
      }.mkString("\n")

    def sendWireData(data: String): Unit = sendWireData(ByteString(data.stripMarginWithNewline("\r\n"), "ASCII"))
    def sendWireData(data: ByteString): Unit = netIn.sendNext(data)

    def send(bytes: ByteString): Unit = sendWireData(bytes)

    def expectWireData(s: String) = netOut.expectUtf8EncodedString(s.stripMarginWithNewline("\r\n"))
    def expectWireData(bs: ByteString) = netOut.expectBytes(bs)
    def expectNoWireData() = netOut.expectNoBytes(noMsgTimeout)

    def expectNetworkClose(): Unit = netOut.expectComplete()
    def closeNetworkInput(): Unit = netIn.sendComplete()
  }

  trait ClientEchoes extends TestSetup {
    override def clientImplementation: Flow[Message, Message, Unit] = echoServer
    def echoServer: Flow[Message, Message, Unit] = Flow[Message]
  }
  trait ClientProbes extends TestSetup {
    lazy val messagesOut = TestPublisher.probe[Message]()
    lazy val messagesIn = TestSubscriber.probe[Message]()

    override def clientImplementation: Flow[Message, Message, Unit] =
      Flow.wrap(Sink(messagesIn), Source(messagesOut))(Keep.none)
  }
}
