package akka.http.model.japi;

public interface StatusCodes {
    StatusCode Continue = akka.http.model.StatusCodes.Continue();
    StatusCode SwitchingProtocols = akka.http.model.StatusCodes.SwitchingProtocols();
    StatusCode Processing = akka.http.model.StatusCodes.Processing();

    StatusCode OK = akka.http.model.StatusCodes.OK();
    StatusCode Created = akka.http.model.StatusCodes.Created();
    StatusCode Accepted = akka.http.model.StatusCodes.Accepted();
    StatusCode NonAuthoritativeInformation = akka.http.model.StatusCodes.NonAuthoritativeInformation();
    StatusCode NoContent = akka.http.model.StatusCodes.NoContent();
    StatusCode ResetContent = akka.http.model.StatusCodes.ResetContent();
    StatusCode PartialContent = akka.http.model.StatusCodes.PartialContent();
    StatusCode MultiStatus = akka.http.model.StatusCodes.MultiStatus();
    StatusCode AlreadyReported = akka.http.model.StatusCodes.AlreadyReported();
    StatusCode IMUsed = akka.http.model.StatusCodes.IMUsed();

    StatusCode MultipleChoices = akka.http.model.StatusCodes.MultipleChoices();
    StatusCode MovedPermanently = akka.http.model.StatusCodes.MovedPermanently();
    StatusCode Found = akka.http.model.StatusCodes.Found();
    StatusCode SeeOther = akka.http.model.StatusCodes.SeeOther();
    StatusCode NotModified = akka.http.model.StatusCodes.NotModified();
    StatusCode UseProxy = akka.http.model.StatusCodes.UseProxy();
    StatusCode TemporaryRedirect = akka.http.model.StatusCodes.TemporaryRedirect();
    StatusCode PermanentRedirect = akka.http.model.StatusCodes.PermanentRedirect();

    StatusCode BadRequest = akka.http.model.StatusCodes.BadRequest();
    StatusCode Unauthorized = akka.http.model.StatusCodes.Unauthorized();
    StatusCode PaymentRequired = akka.http.model.StatusCodes.PaymentRequired();
    StatusCode Forbidden = akka.http.model.StatusCodes.Forbidden();
    StatusCode NotFound = akka.http.model.StatusCodes.NotFound();
    StatusCode MethodNotAllowed = akka.http.model.StatusCodes.MethodNotAllowed();
    StatusCode NotAcceptable = akka.http.model.StatusCodes.NotAcceptable();
    StatusCode ProxyAuthenticationRequired = akka.http.model.StatusCodes.ProxyAuthenticationRequired();
    StatusCode RequestTimeout = akka.http.model.StatusCodes.RequestTimeout();
    StatusCode Conflict = akka.http.model.StatusCodes.Conflict();
    StatusCode Gone = akka.http.model.StatusCodes.Gone();
    StatusCode LengthRequired = akka.http.model.StatusCodes.LengthRequired();
    StatusCode PreconditionFailed = akka.http.model.StatusCodes.PreconditionFailed();
    StatusCode RequestEntityTooLarge = akka.http.model.StatusCodes.RequestEntityTooLarge();
    StatusCode RequestUriTooLong = akka.http.model.StatusCodes.RequestUriTooLong();
    StatusCode UnsupportedMediaType = akka.http.model.StatusCodes.UnsupportedMediaType();
    StatusCode RequestedRangeNotSatisfiable = akka.http.model.StatusCodes.RequestedRangeNotSatisfiable();
    StatusCode ExpectationFailed = akka.http.model.StatusCodes.ExpectationFailed();
    StatusCode EnhanceYourCalm = akka.http.model.StatusCodes.EnhanceYourCalm();
    StatusCode UnprocessableEntity = akka.http.model.StatusCodes.UnprocessableEntity();
    StatusCode Locked = akka.http.model.StatusCodes.Locked();
    StatusCode FailedDependency = akka.http.model.StatusCodes.FailedDependency();
    StatusCode UnorderedCollection = akka.http.model.StatusCodes.UnorderedCollection();
    StatusCode UpgradeRequired = akka.http.model.StatusCodes.UpgradeRequired();
    StatusCode PreconditionRequired = akka.http.model.StatusCodes.PreconditionRequired();
    StatusCode TooManyRequests = akka.http.model.StatusCodes.TooManyRequests();
    StatusCode RequestHeaderFieldsTooLarge = akka.http.model.StatusCodes.RequestHeaderFieldsTooLarge();
    StatusCode RetryWith = akka.http.model.StatusCodes.RetryWith();
    StatusCode BlockedByParentalControls = akka.http.model.StatusCodes.BlockedByParentalControls();
    StatusCode UnavailableForLegalReasons = akka.http.model.StatusCodes.UnavailableForLegalReasons();

    StatusCode InternalServerError = akka.http.model.StatusCodes.InternalServerError();
    StatusCode NotImplemented = akka.http.model.StatusCodes.NotImplemented();
    StatusCode BadGateway = akka.http.model.StatusCodes.BadGateway();
    StatusCode ServiceUnavailable = akka.http.model.StatusCodes.ServiceUnavailable();
    StatusCode GatewayTimeout = akka.http.model.StatusCodes.GatewayTimeout();
    StatusCode HTTPVersionNotSupported = akka.http.model.StatusCodes.HTTPVersionNotSupported();
    StatusCode VariantAlsoNegotiates = akka.http.model.StatusCodes.VariantAlsoNegotiates();
    StatusCode InsufficientStorage = akka.http.model.StatusCodes.InsufficientStorage();
    StatusCode LoopDetected = akka.http.model.StatusCodes.LoopDetected();
    StatusCode BandwidthLimitExceeded = akka.http.model.StatusCodes.BandwidthLimitExceeded();
    StatusCode NotExtended = akka.http.model.StatusCodes.NotExtended();
    StatusCode NetworkAuthenticationRequired = akka.http.model.StatusCodes.NetworkAuthenticationRequired();
    StatusCode NetworkReadTimeout = akka.http.model.StatusCodes.NetworkReadTimeout();
    StatusCode NetworkConnectTimeout = akka.http.model.StatusCodes.NetworkConnectTimeout();
}
