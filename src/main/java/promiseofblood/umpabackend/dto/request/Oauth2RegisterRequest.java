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
   */

  @NotBlank
  @Schema(description = "OpenID Connect 제공자로부터 발급된 idToken")
  private String externalIdToken;

  @NotBlank
  @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
  private String externalAccessToken;

}

