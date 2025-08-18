package promiseofblood.umpabackend.infrastructure.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Oauth2ProfileResponse {

  private String externalIdToken;

  private String externalAccessToken;

  private String providerUid;

  private String profileImageUrl;

  private String username;
}
