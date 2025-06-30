package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

  private String title;

  private String description;

  private Long userId;

  abstract String getCostAndUnit();

}
