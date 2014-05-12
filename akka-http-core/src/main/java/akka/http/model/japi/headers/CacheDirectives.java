package akka.http.model.japi.headers;

public final class CacheDirectives {
    private CacheDirectives() {}

    public static CacheDirective max_age(long deltaSeconds) {
        return new akka.http.model.headers.CacheDirectives.max$minusage(deltaSeconds);
    }
    public static CacheDirective max_stale() {
        return new akka.http.model.headers.CacheDirectives.max$minusstale(akka.japi.Option.none().asScala());
    }
    public static CacheDirective max_stale(long deltaSeconds) {
        return new akka.http.model.headers.CacheDirectives.max$minusstale(akka.japi.Option.some((Object) deltaSeconds).asScala());
    }
    public static CacheDirective min_fresh(long deltaSeconds) {
        return new akka.http.model.headers.CacheDirectives.min$minusfresh(deltaSeconds);
    }

    public static final CacheDirective no_cache = akka.http.model.headers.CacheDirectives.no$minuscache$.MODULE$;
    public static final CacheDirective no_store = akka.http.model.headers.CacheDirectives.no$minusstore$.MODULE$;
    public static final CacheDirective no_transform = akka.http.model.headers.CacheDirectives.no$minustransform$.MODULE$;
    public static final CacheDirective only_if_cached = akka.http.model.headers.CacheDirectives.only$minusif$minuscached$.MODULE$;
    public static final CacheDirective must_revalidate = akka.http.model.headers.CacheDirectives.must$minusrevalidate$.MODULE$;

    public static CacheDirective no_cache(String... fieldNames) {
        return akka.http.model.headers.CacheDirectives.no$minuscache$.MODULE$.apply(fieldNames);
    }
    public static final CacheDirective public_ = akka.http.model.headers.CacheDirectives.public$.MODULE$;
    public static CacheDirective private_(String... fieldNames) {
        return akka.http.model.headers.CacheDirectives.private$.MODULE$.apply(fieldNames);
    }
    public static final CacheDirective proxy_revalidate = akka.http.model.headers.CacheDirectives.proxy$minusrevalidate$.MODULE$;
    public static CacheDirective s_maxage(long deltaSeconds) {
        return new akka.http.model.headers.CacheDirectives.s$minusmaxage(deltaSeconds);
    }
}
