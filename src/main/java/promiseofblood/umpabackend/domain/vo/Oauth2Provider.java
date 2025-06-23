package promiseofblood.umpabackend.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@EqualsAndHashCode
@Component
public class Oauth2Provider {

  private String name;

  private String clientId;

  private String clientSecret;

  private String loginUrl;

  private String tokenUri;

  private String profileUri;

  private String redirectUri;

  @Override
  public String toString() {
    return "Oauth2Provider{" +
      "name='" + name + '\'' +
      ", clientId='" + clientId + '\'' +
      '}';
  }

}
