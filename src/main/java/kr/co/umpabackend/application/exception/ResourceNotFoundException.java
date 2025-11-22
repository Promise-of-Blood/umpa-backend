package kr.co.umpabackend.application.exception;

public class ResourceNotFoundException extends ApplicationException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
