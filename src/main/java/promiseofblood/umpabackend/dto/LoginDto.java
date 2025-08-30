package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import promiseofblood.umpabackend.web.schema.RetrieveFullProfileResponse;

public class LoginDto {

  // ******************
  // * 회원가입 관련 DTO *
  // ******************

  @Getter
  @AllArgsConstructor
  public static class LoginCompleteResponse {

    private RetrieveFullProfileResponse user;

    private JwtPairResponse jwtPair;

    public static LoginCompleteResponse of(RetrieveFullProfileResponse user,
      JwtPairResponse jwtPair) {
      return new LoginCompleteResponse(user, jwtPair);
    }
  }

  @Getter
  @AllArgsConstructor
  public static class JwtPairResponse {

    private String accessToken;

    private String refreshToken;

    public static JwtPairResponse of(String accessToken, String refreshToken) {
      return new JwtPairResponse(accessToken, refreshToken);
    }
  }

  @Getter
  @AllArgsConstructor
  public static class IsUsernameAvailableResponse {

    private String username;

    private boolean isAvailable;

    private String message;
  }

  @Getter
  @AllArgsConstructor
  public static class IsOauth2RegisterAvailableResponse {

    private String providerName;

    private boolean isAvailable;

    private String message;
  }
}
