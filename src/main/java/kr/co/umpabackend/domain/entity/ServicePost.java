package kr.co.umpabackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.umpabackend.domain.entity.abs.TimeStampedEntity;
import kr.co.umpabackend.domain.vo.PostDisplayStatus;
import kr.co.umpabackend.domain.vo.PublishStatus;
import kr.co.umpabackend.domain.vo.UserStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "service_type")
@Getter
@Table(name = "service_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ServicePost extends TimeStampedEntity {

  @Column(name = "service_type", insertable = false, updatable = false)
  private String serviceType;

  private String thumbnailImageUrl;

  private String title;

  private String description;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private PublishStatus publishStatus = PublishStatus.PUBLISHED;

  private LocalDateTime deletedAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  /** 게시물의 표시 상태를 계산하여 반환합니다. 우선순위: 삭제됨 > 작성자 탈퇴 > 모집 상태 */
  public PostDisplayStatus getDisplayStatus() {
    if (this.deletedAt != null) {
      return PostDisplayStatus.DELETED;
    }

    if (this.user != null && this.user.getUserStatus() == UserStatus.WITHDRAWN) {
      return PostDisplayStatus.OWNER_MISSING;
    }

    return this.publishStatus == PublishStatus.PUBLISHED
        ? PostDisplayStatus.PUBLISHED
        : PostDisplayStatus.PAUSED;
  }

  /** 모집을 중단합니다. */
  public void pause() {
    this.publishStatus = PublishStatus.PAUSED;
  }

  /** 모집을 재개합니다. */
  public void publish() {
    this.publishStatus = PublishStatus.PUBLISHED;
  }

  /** 게시물을 삭제합니다 (Soft Delete). */
  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  /** 삭제 여부를 확인합니다. */
  public boolean isDeleted() {
    return this.deletedAt != null;
  }

  /** 작성자가 활성 상태인지 확인합니다. */
  public boolean isOwnerActive() {
    return this.user != null && this.user.getUserStatus() != UserStatus.WITHDRAWN;
  }
}
