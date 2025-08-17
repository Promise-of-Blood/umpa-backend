package promiseofblood.umpabackend.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class Oauth2ProviderDto {

  private String name;

  private String clientId;

  private String loginUrl;

  private String tokenUri;

  private String profileUri;

  private String redirectUri;
}
