package promiseofblood.umpabackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@SuperBuilder
@Table(name = "social_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser extends TimeStampedEntity {

  private String socialId;

  private String accessToken;

  private String refreshToken;
  
  @ManyToOne
  @JoinColumn(name = "oauth2_provider_id")
  private Oauth2Provider oauth2Provider;

}
