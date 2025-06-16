package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2RegisterRequest {

  /*
   * < --- Oauth2 회원가입 공통 요소 --->
   * - idToken
   * - accessToken
   * - refreshToken
   * <--- 회원가입 공통 요소 --->
   * - 닉네임
   * - 전공
   * - 프로필사진
   * - 성별
   */

  @NotBlank
  @Schema(description = "OpenID Connect 제공자로부터 발급된 idToken")
  private String externalIdToken;

  @NotBlank
  @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
  private String externalAccessToken;

  @NotBlank
  @Schema(description = "oauth2 제공자로부터 발급된 리프레시 토큰")
  private String externalRefreshToken;

  @NotBlank(message = "닉네임은 필수입니다.")
  @Size(min = 2, max = 20, message = "닉네임은 2글자 이상 20글자 이하로 입력해주세요.")
  @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "닉네임은 한글, 영문(대소문자), 숫자만 사용할 수 있습니다.")
  @Schema(description = "닉네임", example = "Goddessana")
  private String username;

  @NotBlank(message = "전공은 필수입니다.")
  @Schema(description = "전공", example = "BASS")
  private String major;

  MultipartFile profileImage;

  @NotBlank(message = "성별은 필수입니다.")
  @Schema(description = "성별", example = "MALE")
  private String gender;
}

