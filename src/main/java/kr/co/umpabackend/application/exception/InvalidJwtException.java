package kr.co.umpabackend.application.exception;

public class InvalidJwtException extends ApplicationException {

  public InvalidJwtException(String message) {
    super(message);
  }
}
