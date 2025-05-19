package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;

@Entity
@Getter
@SuperBuilder
@Table(name = "teacher_links")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherLink extends TimeStampedEntity {

  private String link;

  @ManyToOne
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  public void setProfile(TeacherProfile teacherProfile) {
    this.teacherProfile = teacherProfile;
  }

}
