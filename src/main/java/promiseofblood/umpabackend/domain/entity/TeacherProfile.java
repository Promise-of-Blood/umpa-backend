package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;


@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "teacher_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherProfile extends TimeStampedEntity {

  private String description;

  @Enumerated(EnumType.STRING)
  private Major major;

  @Enumerated(EnumType.STRING)
  private Region lessonRegion;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherCareer> careers;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherLink> links;

  public static TeacherProfile from(TeacherProfileRequest request) {

    return TeacherProfile.builder()
      .description(request.getDescription())
      .major(request.getMajor())
      .lessonRegion(request.getLessonRegion())
      .careers(request.getCareers().stream()
        .map(TeacherCareer::from)
        .toList())
      .links(request.getLinks().stream()
        .map(TeacherLink::from)
        .toList())
      .build();
  }

  public void update(TeacherProfileRequest request) {
    if (request.getDescription() != null) {
      this.description = request.getDescription();
    }
    if (request.getMajor() != null) {
      this.major = request.getMajor();
    }
    if (request.getLessonRegion() != null) {
      this.lessonRegion = request.getLessonRegion();
    }
    if (request.getCareers() != null) {
      this.careers.clear();
      for (TeacherProfileRequest.TeacherCareerRequest careerRequest : request.getCareers()) {
        TeacherCareer career = TeacherCareer.from(careerRequest);
        this.careers.add(career);
        career.setTeacherProfile(this);
      }
    }
    if (request.getLinks() != null) {
      this.links.clear();
      for (String link : request.getLinks()) {
        TeacherLink teacherLink = TeacherLink.from(link);
        this.links.add(teacherLink);
        teacherLink.setTeacherProfile(this);
      }
    }
  }

}
