package promiseofblood.umpabackend.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@ToString
@AllArgsConstructor
public class Oauth2TokenResponse {

  @JsonProperty("id_token")
  private String idToken;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;
}
