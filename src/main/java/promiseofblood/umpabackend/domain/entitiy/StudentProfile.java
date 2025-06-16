package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;

@Entity
@Getter
@SuperBuilder
@Table(name = "student_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentProfile extends TimeStampedEntity {

  @Enumerated(EnumType.STRING)
  private Major major;

  @Enumerated(EnumType.STRING)
  private LessonStyle lessonStyle;


}
