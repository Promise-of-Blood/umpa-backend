package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@Getter
@SuperBuilder
@Table(name = "oauth2_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth2User extends TimeStampedEntity {

  private Long userId;

  private String providerName;

  private String providerUid;

  private String profileImageUrl;

  private String username;

  public void assignUserId(Long userId) {
    this.userId = userId;
  }
}
