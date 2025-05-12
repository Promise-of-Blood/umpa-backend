package promiseofblood.umpabackend.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2TokenResponse {

  @JsonProperty("access_token")
  private String accessToken;
}
