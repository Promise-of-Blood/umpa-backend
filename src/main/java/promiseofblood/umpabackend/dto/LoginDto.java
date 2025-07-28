package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LoginDto {

  @Getter
  @AllArgsConstructor
  public static class RegisterCompleteResponse {

    private UserDto.ProfileResponse user;

    private JwtPairResponse jwtPair;

    public static RegisterCompleteResponse of(
      UserDto.ProfileResponse user, JwtPairResponse jwtPair) {
      return new RegisterCompleteResponse(user, jwtPair);
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

}
