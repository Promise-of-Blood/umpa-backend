package promiseofblood.umpabackend.dto.external;

import lombok.*;

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
