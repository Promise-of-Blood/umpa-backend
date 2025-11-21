package kr.co.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import kr.co.umpabackend.domain.vo.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateArticleRequest {

  @Schema(description = "아티클 제목", example = "Spring Boot 튜토리얼")
  @NotBlank(message = "제목은 필수입니다")
  private final String title;

  @Schema(description = "아티클 내용 (마크다운)", example = "# Spring Boot 시작하기\n\n...")
  @NotBlank(message = "내용은 필수입니다")
  private final String content;

  @Schema(description = "아티클 상태", example = "DRAFT")
  @NotBlank(message = "상태는 필수입니다")
  private final ArticleStatus status;
}
