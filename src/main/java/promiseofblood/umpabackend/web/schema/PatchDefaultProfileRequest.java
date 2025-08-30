package promiseofblood.umpabackend.web.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.ProfileType;

@Getter
@AllArgsConstructor
public class PatchDefaultProfileRequest {

  @Nullable
  @Schema(description = "사용자 닉네임", example = "홍길동")
  @Size(max = 8, message = "사용자 닉네임은 최대 8자까지 가능합니다.")
  private final String username;

  @Nullable
  @Schema(description = "성별", example = "MALE")
  private final Gender gender;

  @Nullable
  @Schema(type = "enum", description = "현재 활성화된 프로필 타입")
  private final ProfileType profileType;

  @Nullable
  @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
  private final MultipartFile profileImage;
}
