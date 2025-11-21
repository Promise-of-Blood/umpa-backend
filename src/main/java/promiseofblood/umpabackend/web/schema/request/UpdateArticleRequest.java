package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.domain.vo.ArticleStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {

  @Schema(description = "아티클 제목", example = "Spring Boot 튜토리얼 (수정)")
  private String title;

  @Schema(description = "아티클 내용 (마크다운)", example = "# Spring Boot 시작하기 (수정)\n\n...")
  private String content;

  @Schema(description = "아티클 상태", example = "PUBLISHED")
  private ArticleStatus status;
}
