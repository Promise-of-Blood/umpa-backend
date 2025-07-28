package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.ProfileType;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;


@Entity
@Getter
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity {

  // 로그인용 ID, 비밀번호(일반 회원가입)
  @Column(nullable = false, unique = true)
  private String loginId;

  private String password;

  // 회원 상태, 역할
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  // 닉네임, 성별, 프로필사진
  @Column(nullable = false, unique = true)
  private String username;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String profileImageUrl;

  // 학생 프로필, 선생님 프로필
  @Enumerated(EnumType.STRING)
  private ProfileType profileType;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "student_profile_id")
  private StudentProfile studentProfile;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  // 소셜 로그인 유저
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "oauth2_user_id")
  private Oauth2User oauth2User;

  public static User register(String loginId, Status status, Role role, String username,
    ProfileType profileType) {

    return User.builder()
      .loginId(loginId)
      .status(status)
      .role(role)
      .username(username)
      .profileType(profileType)
      .build();
  }

  public void patchUsername(String username) {
    this.username = username;
  }

  public void patchProfileType(ProfileType profileType) {
    this.profileType = profileType;
  }

  public void patchGender(Gender gender) {
    this.gender = gender;
  }

  public void patchProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public void patchStudentProfile(StudentProfile studentProfile) {
    this.studentProfile = studentProfile;
  }

  public void patchTeacherProfile(TeacherProfile teacherProfile) {
    this.teacherProfile = teacherProfile;
  }
}
