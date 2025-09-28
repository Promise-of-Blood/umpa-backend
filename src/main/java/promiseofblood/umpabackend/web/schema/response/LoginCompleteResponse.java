package promiseofblood.umpabackend.web.schema.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCompleteResponse {

  private RetrieveFullProfileResponse user;

  private JwtPairResponse jwtPair;

  public static LoginCompleteResponse of(
      RetrieveFullProfileResponse user, String accessToken, String refreshToken) {
    return new LoginCompleteResponse(user, JwtPairResponse.of(accessToken, refreshToken));
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
}
