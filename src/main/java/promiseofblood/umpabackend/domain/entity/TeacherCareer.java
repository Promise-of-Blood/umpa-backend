package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.dto.TeacherProfileDto;


@Entity
@Getter
@Setter
@SuperBuilder
@ToString
@Table(name = "teacher_careers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherCareer extends TimeStampedEntity {

  private boolean isRepresentative;

  private String title;

  private YearMonth start;

  @Column(name = "\"end\"")
  private YearMonth end;

  @ManyToOne
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  public static TeacherCareer from(
    TeacherProfileDto.TeacherProfileRequest.TeacherCareerRequest request) {
    return TeacherCareer.builder()
      .isRepresentative(request.isRepresentative())
      .title(request.getTitle())
      .start(request.getStart())
      .end(request.getEnd())
      .build();
  }

}
