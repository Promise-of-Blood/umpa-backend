package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

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

//  @Enumerated(EnumType.STRING)
//  private List<College> preferredColleges;

  @Enumerated(EnumType.STRING)
  private Grade grade;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  @Enumerated(EnumType.STRING)
  private List<WeekDay> weekDays;

  private String lessonRequirements;

}
