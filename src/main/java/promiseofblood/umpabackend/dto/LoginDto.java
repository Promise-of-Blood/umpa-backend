package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.vo.ProfileType;

@Getter
@Builder
@ToString
public class LoginDto {

  // ******************
  // * 회원가입 관련 DTO *
  // ******************

  @Getter
  @AllArgsConstructor
  public static class LoginIdPasswordRegisterRequest {

    private String username;

    private ProfileType profileType;

    private String loginId;

    private String password;

    public static LoginIdPasswordRegisterRequest of(
      String username, ProfileType profileType, String loginId, String password) {
      return new LoginIdPasswordRegisterRequest(username, profileType, loginId, password);
    }

  }

  @Getter
  public static class LoginIdPasswordLoginRequest {

    private String loginId;

    private String password;

  }

  @Getter
  @AllArgsConstructor
  public static class LoginCompleteResponse {

    private UserDto.ProfileResponse user;

    private JwtPairResponse jwtPair;

    public static LoginCompleteResponse of(UserDto.ProfileResponse user, JwtPairResponse jwtPair) {
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

}
