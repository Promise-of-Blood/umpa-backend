package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.ProfileType;


public class LoginDto {

  // ******************
  // * 회원가입 관련 DTO *
  // ******************

  @Getter
  @AllArgsConstructor
  public static class LoginIdPasswordRegisterRequest {

    @NotBlank
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String username;

    @Schema(description = "성별", example = "MALE")
    private Gender gender;

    @Schema(type = "enum", description = "현재 활성화된 프로필 타입")
    private ProfileType profileType;

    @NotBlank
    @Schema(description = "로그인 아이디")
    private String loginId;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
    
    @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
    private MultipartFile profileImage;

  }

  @Builder
  @Getter
  @AllArgsConstructor
  public static class Oauth2RegisterRequest {

    @NotBlank
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String username;

    @Schema(description = "성별", example = "MALE")
    private Gender gender;

    @Schema(type = "enum", description = "현재 활성화된 프로필 타입")
    private ProfileType profileType;

    @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
    private MultipartFile profileImage;

    @NotBlank
    @Schema(description = "OpenID Connect 제공자로부터 발급된 idToken")
    private String externalIdToken;

    @NotBlank
    @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
    private String externalAccessToken;

  }

  // ****************
  // * 로그인 관련 DTO *
  // ****************
  @Getter
  public static class Oauth2LoginRequest {

    private String externalIdToken;

    private String externalAccessToken;

  }

  @Getter
  public static class LoginIdPasswordLoginRequest {

    private String loginId;

    private String password;

  }

  @Getter
  @NoArgsConstructor
  public static class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;

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
