package promiseofblood.umpabackend.domain.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "service_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ServicePost extends TimeStampedEntity {

  @Column(name = "service_type", insertable = false, updatable = false)
  private String serviceType;

  private String thumbnailImageUrl;

  private String title;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "servicePost", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  abstract public String getCostAndUnit();
}
