package promiseofblood.umpabackend.core.exception;

public class Oauth2UserAlreadyExists extends RuntimeException {

  public Oauth2UserAlreadyExists(String message) {
    super(message);
  }
}
