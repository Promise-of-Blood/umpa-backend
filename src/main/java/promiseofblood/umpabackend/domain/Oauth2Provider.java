package promiseofblood.umpabackend.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "oauth2_providers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth2Provider extends IdEntity {

  private String name;

  private String clientId;

  private String clientSecret;

  private String loginUrl;

  private List<String> redirectUris;

  private String tokenUri;

  private String profileUri;

}
