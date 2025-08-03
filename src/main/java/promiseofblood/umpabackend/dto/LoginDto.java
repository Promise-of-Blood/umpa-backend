package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

  @Builder
  @Getter
  @AllArgsConstructor
  public static class Oauth2RegisterRequest {

    private String username;

    private ProfileType profileType;

    @NotBlank
    @Schema(description = "OpenID Connect 제공자로부터 발급된 idToken")
    private String externalIdToken;

    @NotBlank
    @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
    private String externalAccessToken;

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
