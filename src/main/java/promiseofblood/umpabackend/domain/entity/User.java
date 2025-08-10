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
import promiseofblood.umpabackend.domain.vo.UserStatus;


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
  private UserStatus userStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  // 닉네임, 성별, 프로필사진
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(nullable = false)
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

  public static User register(
    String loginId, Gender gender, UserStatus userStatus, Role role, String username,
    ProfileType profileType, String profileImageUrl) {

    return User.builder()
      .loginId(loginId)
      .gender(gender)
      .userStatus(userStatus)
      .role(role)
      .username(username)
      .profileType(profileType)
      .profileImageUrl(profileImageUrl)
      .build();
  }

  public static User register(String loginId, Gender gender, UserStatus userStatus, Role role,
    String username,
    ProfileType profileType, String profileImageUrl, Oauth2User oauth2User) {

    return User.builder()
      .loginId(loginId)
      .gender(gender)
      .userStatus(userStatus)
      .role(role)
      .username(username)
      .profileType(profileType)
      .profileImageUrl(profileImageUrl)
      .oauth2User(oauth2User)
      .build();
  }

  public void patchStudentProfile(StudentProfile studentProfile) {
    this.studentProfile = studentProfile;
  }

  public void patchTeacherProfile(TeacherProfile teacherProfile) {
    this.teacherProfile = teacherProfile;
  }

  public void patchDefaultProfile(
    String username, Gender gender, String profileImageUrl, ProfileType profileType
  ) {
    if (username != null) {
      this.username = username;
    }
    if (gender != null) {
      this.gender = gender;
    }
    if (profileImageUrl != null) {
      this.profileImageUrl = profileImageUrl;
    }
    if (profileType != null) {
      this.profileType = profileType;
    }
  }
}
