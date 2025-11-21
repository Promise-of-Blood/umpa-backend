package kr.co.umpabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.umpabackend.domain.entity.abs.TimeStampedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "teacher_profile_links")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherLink extends TimeStampedEntity {

  private String link;

  @ManyToOne
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  public static TeacherLink from(String link) {
    return TeacherLink.builder().link(link).build();
  }
}
