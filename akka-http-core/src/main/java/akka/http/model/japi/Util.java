package akka.http.model.japi;

import akka.japi.Option;
import org.reactivestreams.api.Producer;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector$;

import java.util.Arrays;
import java.util.Map;

public abstract class Util {
    @SuppressWarnings("unchecked") // no support for covariance of option in Java
    public static <U, T extends U> Option<U> convertOption(scala.Option<T> o) {
        return (Option<U>)(Option) akka.japi.Option.fromScalaOption(o);
    }
    @SuppressWarnings("unchecked") // no support for covariance of Producer in Java
    public static <U, T extends U> Producer<U> convertProducer(Producer<T> p) {
        return (Producer<U>)(Producer) p;
    }
    @SuppressWarnings("unchecked")
    public static <T, U extends T> Producer<U> upcastProducer(Producer<T> p) {
        return (Producer<U>)(Producer) p;
    }
    @SuppressWarnings("unchecked")
    public static scala.collection.immutable.Map<String, String> convertMapToScala(Map<String, String> map) {
        return Map$.MODULE$.apply(scala.collection.JavaConverters.asScalaMapConverter(map).asScala().toSeq());
    }
    @SuppressWarnings("unchecked")
    public static <T, U extends T> Seq<U> convertIterable(Iterable<T> els) {
        return scala.collection.JavaConverters.asScalaIterableConverter((Iterable<U>)els).asScala().toVector();
    }
    @SuppressWarnings("unchecked")
    public static <T, U extends T> Seq<U> convertArray(T[] els) {
        return convertIterable(Arrays.asList(els));
    }
}
