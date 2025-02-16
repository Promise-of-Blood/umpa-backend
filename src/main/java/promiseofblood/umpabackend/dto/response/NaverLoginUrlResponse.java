package promiseofblood.umpabackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class NaverLoginUrlResponse {
  private String url;
}
