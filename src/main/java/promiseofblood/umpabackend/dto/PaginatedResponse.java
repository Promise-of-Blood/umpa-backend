package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@RequiredArgsConstructor
public class PaginatedResponse<T> {

  private final int page;
  private final int count;
  private final int totalPage;
  private final long totalCount;

  private final List<T> items;


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
