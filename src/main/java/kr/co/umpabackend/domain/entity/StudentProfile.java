package kr.co.umpabackend.domain.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import kr.co.umpabackend.domain.entity.abs.TimeStampedEntity;
import kr.co.umpabackend.domain.vo.College;
import kr.co.umpabackend.domain.vo.Grade;
import kr.co.umpabackend.domain.vo.Major;
import kr.co.umpabackend.web.schema.request.PatchStudentProfileRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "student_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentProfile extends TimeStampedEntity {

  @Enumerated(EnumType.STRING)
  private Major major;

  @ElementCollection(targetClass = College.class)
  @CollectionTable(
      name = "student_profile_preferred_colleges",
      joinColumns = @JoinColumn(name = "student_profile_id"))
  @Enumerated(EnumType.STRING)
  private List<College> preferredColleges;

  @Enumerated(EnumType.STRING)
  private Grade grade;

  public static StudentProfile empty() {
    var studentProfile = new StudentProfile();
    studentProfile.preferredColleges = new ArrayList<>();
    return studentProfile;
  }

  public void update(PatchStudentProfileRequest request) {
    if (request.getMajor() != null) {
      this.major = request.getMajor();
    }
    if (request.getPreferredColleges() != null) {
      this.preferredColleges = request.getPreferredColleges();
    }
    if (request.getGrade() != null) {
      this.grade = request.getGrade();
    }
  }
}
