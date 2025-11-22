package kr.co.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.umpabackend.domain.vo.Gender;
import kr.co.umpabackend.domain.vo.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@AllArgsConstructor
public class RegisterByOauth2Request {

  @NotBlank
  @Schema(description = "사용자 닉네임", example = "홍길동")
  private final String username;

  @NotNull @Schema(type = "enum", description = "현재 활성화된 프로필 타입")
  private final ProfileType profileType;

  @NotBlank
  @Schema(description = "OpenID Connect 제공자로부터 발급된 idToken")
  private final String externalIdToken;

  @NotBlank
  @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
  private final String externalAccessToken;

  // 선택 필드들
  @Schema(description = "성별", example = "MALE")
  private final Gender gender;

  @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
  private final MultipartFile profileImage;
}
