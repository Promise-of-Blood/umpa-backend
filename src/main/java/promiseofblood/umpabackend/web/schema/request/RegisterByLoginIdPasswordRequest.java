package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.ProfileType;

@Getter
@AllArgsConstructor
public class RegisterByLoginIdPasswordRequest {

  @NotBlank
  @Schema(description = "사용자 닉네임", example = "홍길동")
  private String username;

  @NotNull
  @Schema(description = "성별", example = "MALE")
  private Gender gender;

  @NotNull
  @Schema(type = "enum", description = "현재 활성화된 프로필 타입")
  private ProfileType profileType;

  @NotBlank
  @Schema(description = "로그인 아이디")
  private String loginId;

  @NotBlank
  @Schema(description = "비밀번호")
  private String password;

  @NotNull
  @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
  private MultipartFile profileImage;
}
