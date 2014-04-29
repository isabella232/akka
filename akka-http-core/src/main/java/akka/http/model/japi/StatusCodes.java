package akka.http.model.japi;

public interface StatusCodes {
    final StatusCode Continue = akka.http.model.StatusCodes.Continue();
    final StatusCode SwitchingProtocols = akka.http.model.StatusCodes.SwitchingProtocols();
    final StatusCode Processing = akka.http.model.StatusCodes.Processing();

    final StatusCode OK = akka.http.model.StatusCodes.OK();
    final StatusCode Created = akka.http.model.StatusCodes.Created();
    final StatusCode Accepted = akka.http.model.StatusCodes.Accepted();
    final StatusCode NonAuthoritativeInformation = akka.http.model.StatusCodes.NonAuthoritativeInformation();
    final StatusCode NoContent = akka.http.model.StatusCodes.NoContent();
    final StatusCode ResetContent = akka.http.model.StatusCodes.ResetContent();
    final StatusCode PartialContent = akka.http.model.StatusCodes.PartialContent();
    final StatusCode MultiStatus = akka.http.model.StatusCodes.MultiStatus();
    final StatusCode AlreadyReported = akka.http.model.StatusCodes.AlreadyReported();
    final StatusCode IMUsed = akka.http.model.StatusCodes.IMUsed();

    final StatusCode MultipleChoices = akka.http.model.StatusCodes.MultipleChoices();
    final StatusCode MovedPermanently = akka.http.model.StatusCodes.MovedPermanently();
    final StatusCode Found = akka.http.model.StatusCodes.Found();
    final StatusCode SeeOther = akka.http.model.StatusCodes.SeeOther();
    final StatusCode NotModified = akka.http.model.StatusCodes.NotModified();
    final StatusCode UseProxy = akka.http.model.StatusCodes.UseProxy();
    final StatusCode TemporaryRedirect = akka.http.model.StatusCodes.TemporaryRedirect();
    final StatusCode PermanentRedirect = akka.http.model.StatusCodes.PermanentRedirect();

    final StatusCode BadRequest = akka.http.model.StatusCodes.BadRequest();
    final StatusCode Unauthorized = akka.http.model.StatusCodes.Unauthorized();
    final StatusCode PaymentRequired = akka.http.model.StatusCodes.PaymentRequired();
    final StatusCode Forbidden = akka.http.model.StatusCodes.Forbidden();
    final StatusCode NotFound = akka.http.model.StatusCodes.NotFound();
    final StatusCode MethodNotAllowed = akka.http.model.StatusCodes.MethodNotAllowed();
    final StatusCode NotAcceptable = akka.http.model.StatusCodes.NotAcceptable();
    final StatusCode ProxyAuthenticationRequired = akka.http.model.StatusCodes.ProxyAuthenticationRequired();
    final StatusCode RequestTimeout = akka.http.model.StatusCodes.RequestTimeout();
    final StatusCode Conflict = akka.http.model.StatusCodes.Conflict();
    final StatusCode Gone = akka.http.model.StatusCodes.Gone();
    final StatusCode LengthRequired = akka.http.model.StatusCodes.LengthRequired();
    final StatusCode PreconditionFailed = akka.http.model.StatusCodes.PreconditionFailed();
    final StatusCode RequestEntityTooLarge = akka.http.model.StatusCodes.RequestEntityTooLarge();
    final StatusCode RequestUriTooLong = akka.http.model.StatusCodes.RequestUriTooLong();
    final StatusCode UnsupportedMediaType = akka.http.model.StatusCodes.UnsupportedMediaType();
    final StatusCode RequestedRangeNotSatisfiable = akka.http.model.StatusCodes.RequestedRangeNotSatisfiable();
    final StatusCode ExpectationFailed = akka.http.model.StatusCodes.ExpectationFailed();
    final StatusCode EnhanceYourCalm = akka.http.model.StatusCodes.EnhanceYourCalm();
    final StatusCode UnprocessableEntity = akka.http.model.StatusCodes.UnprocessableEntity();
    final StatusCode Locked = akka.http.model.StatusCodes.Locked();
    final StatusCode FailedDependency = akka.http.model.StatusCodes.FailedDependency();
    final StatusCode UnorderedCollection = akka.http.model.StatusCodes.UnorderedCollection();
    final StatusCode UpgradeRequired = akka.http.model.StatusCodes.UpgradeRequired();
    final StatusCode PreconditionRequired = akka.http.model.StatusCodes.PreconditionRequired();
    final StatusCode TooManyRequests = akka.http.model.StatusCodes.TooManyRequests();
    final StatusCode RequestHeaderFieldsTooLarge = akka.http.model.StatusCodes.RequestHeaderFieldsTooLarge();
    final StatusCode RetryWith = akka.http.model.StatusCodes.RetryWith();
    final StatusCode BlockedByParentalControls = akka.http.model.StatusCodes.BlockedByParentalControls();
    final StatusCode UnavailableForLegalReasons = akka.http.model.StatusCodes.UnavailableForLegalReasons();

    final StatusCode InternalServerError = akka.http.model.StatusCodes.InternalServerError();
    final StatusCode NotImplemented = akka.http.model.StatusCodes.NotImplemented();
    final StatusCode BadGateway = akka.http.model.StatusCodes.BadGateway();
    final StatusCode ServiceUnavailable = akka.http.model.StatusCodes.ServiceUnavailable();
    final StatusCode GatewayTimeout = akka.http.model.StatusCodes.GatewayTimeout();
    final StatusCode HTTPVersionNotSupported = akka.http.model.StatusCodes.HTTPVersionNotSupported();
    final StatusCode VariantAlsoNegotiates = akka.http.model.StatusCodes.VariantAlsoNegotiates();
    final StatusCode InsufficientStorage = akka.http.model.StatusCodes.InsufficientStorage();
    final StatusCode LoopDetected = akka.http.model.StatusCodes.LoopDetected();
    final StatusCode BandwidthLimitExceeded = akka.http.model.StatusCodes.BandwidthLimitExceeded();
    final StatusCode NotExtended = akka.http.model.StatusCodes.NotExtended();
    final StatusCode NetworkAuthenticationRequired = akka.http.model.StatusCodes.NetworkAuthenticationRequired();
    final StatusCode NetworkReadTimeout = akka.http.model.StatusCodes.NetworkReadTimeout();
    final StatusCode NetworkConnectTimeout = akka.http.model.StatusCodes.NetworkConnectTimeout();
}
