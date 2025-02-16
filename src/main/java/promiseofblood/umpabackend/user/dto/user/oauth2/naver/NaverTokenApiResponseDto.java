package promiseofblood.umpabackend.user.dto.user.oauth2.naver;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;

@Getter
public class NaverTokenApiResponseDto {

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
