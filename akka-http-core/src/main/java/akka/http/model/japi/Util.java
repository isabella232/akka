package akka.http.model.japi;

import akka.http.model.*;
import akka.japi.Option;
import org.reactivestreams.api.Producer;
import scala.None;
import scala.None$;
import scala.NotImplementedError;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Seq;

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
    @SuppressWarnings("unchecked") // contains an upcast
    public static <T, U extends T> scala.Option<U> convertOptionToScala(Option<T> o) {
        return ((Option<U>) o).asScala();
    }

    public static final scala.collection.immutable.Map<String, String> emptyMap =
        Map$.MODULE$.<String, String>empty();

    public static final None$ noneValue = None$.MODULE$;
    @SuppressWarnings("unchecked")
    public static <T> scala.Option<T> scalaNone() {
        return (scala.Option<T>) noneValue;
    }

    @SuppressWarnings("unchecked")
    public static <T, U extends T> Seq<U> convertIterable(Iterable<T> els) {
        return scala.collection.JavaConverters.asScalaIterableConverter((Iterable<U>)els).asScala().toVector();
    }
    @SuppressWarnings("unchecked")
    public static <T, U extends T> Seq<U> convertArray(T[] els) {
        return convertIterable(Arrays.asList(els));
    }

    public static akka.http.model.Uri convertUriToScala(Uri uri) {
        return ((JavaUri) uri).uri();
    }
}
