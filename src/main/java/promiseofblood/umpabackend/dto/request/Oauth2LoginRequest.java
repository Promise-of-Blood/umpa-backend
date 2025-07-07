package promiseofblood.umpabackend.dto.request;

import lombok.Getter;

@Getter
public class Oauth2LoginRequest {

  private String externalIdToken;
  
  private String externalAccessToken;

}
