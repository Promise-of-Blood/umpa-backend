package kr.co.umpabackend.application.exception;

public class Oauth2UserAlreadyExists extends RuntimeException {

  public Oauth2UserAlreadyExists(String message) {
    super(message);
  }
}
