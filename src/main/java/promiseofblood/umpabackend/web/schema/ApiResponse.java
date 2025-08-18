package promiseofblood.umpabackend.web.schema;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class ApiResponse {

  @Builder
  public record PaginatedResponse<T>(
    int page, int count, int totalPage, long totalCount, List<T> items) {

    public static <T> PaginatedResponse<T> from(Page<T> page) {

      return PaginatedResponse.<T>builder()
        .page(page.getNumber())
        .count(page.getNumberOfElements())
        .totalPage(page.getTotalPages())
        .totalCount(page.getTotalElements())
        .items(page.getContent())
        .build();
    }
  }

  @Getter
  @AllArgsConstructor
  public static class ExceptionResponse {

    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

  }
}
