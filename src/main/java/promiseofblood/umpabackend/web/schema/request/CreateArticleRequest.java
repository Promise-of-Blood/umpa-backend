package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {

  @Schema(description = "아티클 제목", example = "Spring Boot 튜토리얼")
  @NotBlank(message = "제목은 필수입니다")
  private String title;

  @Schema(description = "아티클 내용 (마크다운)", example = "# Spring Boot 시작하기\n\n...")
  @NotBlank(message = "내용은 필수입니다")
  private String content;
}
