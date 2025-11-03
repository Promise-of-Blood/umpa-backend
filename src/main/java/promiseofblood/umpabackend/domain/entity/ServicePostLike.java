package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@SuperBuilder
@Getter
@Table(
    name = "service_post_likes",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_user_service_post",
          columnNames = {"user_id", "service_post_id"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServicePostLike extends TimeStampedEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "service_post_id", nullable = false)
  private ServicePost servicePost;
}
