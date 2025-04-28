package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;

@Getter
public enum Oauth2Provider {

  KAKAO("KAKAO", "kakao_client_id", "kakao_client_secret"),
  NAVER("NAVER", "naver_client_id", "naver_client_secret"),
  APPLE("APPLE", "apple_client_id", "apple_client_secret"),
  GOOGLE("GOOGLE", "google_client_id", "google_client_secret");

  private final String name;
  private final String clientId;
  private final String clientSecret;

  Oauth2Provider(String name, String clientId, String clientSecret) {
    this.name = name;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

}
