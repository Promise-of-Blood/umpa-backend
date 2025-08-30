package promiseofblood.umpabackend.application.exception;

public class NotSupportedOauth2ProviderException extends IllegalArgumentException {

  public NotSupportedOauth2ProviderException(String message) {
    super(message);
  }
}
