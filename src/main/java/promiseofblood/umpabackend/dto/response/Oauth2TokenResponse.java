package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth2TokenResponse {

  private String accessToken;

  private String refreshToken;

}
