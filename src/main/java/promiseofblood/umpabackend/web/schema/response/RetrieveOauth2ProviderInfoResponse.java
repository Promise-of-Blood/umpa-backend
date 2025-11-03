package promiseofblood.umpabackend.web.schema.response;

import lombok.*;

@Getter
@Setter
@Builder
public class RetrieveOauth2ProviderInfoResponse {

  private String name;

  private String clientId;

  private String loginUrl;

  private String tokenUri;

  private String profileUri;

  private String redirectUri;
}
