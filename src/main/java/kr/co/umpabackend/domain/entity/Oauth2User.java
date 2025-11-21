package kr.co.umpabackend.domain.entity;

import jakarta.persistence.*;
import kr.co.umpabackend.domain.entity.abs.TimeStampedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
