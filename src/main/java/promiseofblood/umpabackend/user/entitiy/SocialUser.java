package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.common.entitiy.TimeStampedEntity;


@Entity
@Getter
@SuperBuilder
@Table(name = "social_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser extends TimeStampedEntity {

  private String socialId;

  private String accessToken;

  private String refreshToken;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "oauth2_provider_id")
  private Oauth2Provider oauth2Provider;

}
