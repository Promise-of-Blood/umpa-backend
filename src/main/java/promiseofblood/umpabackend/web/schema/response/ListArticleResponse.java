package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListArticleResponse {

  @Schema(description = "아티클 ID", example = "1")
  private Long id;

  @Schema(description = "아티클 제목", example = "Spring Boot 튜토리얼")
  private String title;

  @Schema(description = "작성자 이름", example = "홍길동")
  private String authorName;

  @Schema(description = "조회수", example = "42")
  private Long viewCount;

  @Schema(description = "생성일시", example = "2024-01-01T00:00:00")
  private LocalDateTime createdAt;
}
