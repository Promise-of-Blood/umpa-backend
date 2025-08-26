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
import promiseofblood.umpabackend.dto.TeacherProfileDto;

@Entity
@Getter
@Table(name = "teacher_profiles")
@NoArgsConstructor
public class TeacherProfile extends TimeStampedEntity {

  private String keyphrase;

  private String description;

  @Enumerated(EnumType.STRING)
  private Major major;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherCareer> careers;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherLink> links;

  public boolean isProfileComplete() {
    return description != null && !description.isEmpty() &&
      major != null &&
      careers != null && !careers.isEmpty();
  }

  public void update(TeacherProfileDto.TeacherProfileRequest request) {
    if (request.getKeyphrase() != null) {
      this.keyphrase = request.getKeyphrase();
    }
    if (request.getDescription() != null) {
      this.description = request.getDescription();
    }
    if (request.getMajor() != null) {
      this.major = request.getMajor();
    }
    if (request.getCareers() != null) {
      this.careers.clear();
      for (TeacherProfileDto.TeacherProfileRequest.TeacherCareerRequest careerRequest :
        request.getCareers()) {
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
