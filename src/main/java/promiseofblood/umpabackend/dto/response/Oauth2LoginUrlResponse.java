package promiseofblood.umpabackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2LoginUrlResponse {
  private String url;
}
