package promiseofblood.umpabackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenResponse {

  @JsonProperty("access_token")
  String accessToken;

  @JsonProperty("refresh_token")
  String refreshToken;

}
