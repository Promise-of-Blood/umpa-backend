package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class DefaultProfileRequest {

  @Schema(description = "사용자 닉네임", example = "홍길동")
  private String username;

  @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
  private MultipartFile profileImage;

}
