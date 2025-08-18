package promiseofblood.umpabackend.core.exception;

public class InvalidJwtException extends RuntimeException {

  public InvalidJwtException(String message) {
    super(message);
  }
}
