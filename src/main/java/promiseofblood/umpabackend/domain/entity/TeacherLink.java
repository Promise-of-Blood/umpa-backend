package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "teacher_links")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherLink extends TimeStampedEntity {

  private String link;

  @ManyToOne
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

}
