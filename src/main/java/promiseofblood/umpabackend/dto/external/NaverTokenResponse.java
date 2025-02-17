package promiseofblood.umpabackend.dto.external;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;

@Getter
public class NaverTokenResponse {

  @JsonAlias("access_token")
  private String accessToken;

  @JsonAlias("refresh_token")
  private String refreshToken;

  @JsonAlias("token_type")
  private String tokenType;

  @JsonAlias("expires_in")
  private String expiresIn;

  private String error;

  @JsonAlias("error_description")
  private String errorDescription;

}
