package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;


@Entity
@Getter
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity {

  // 닉네임
  private String username;
  // 성별
  @Enumerated(EnumType.STRING)
  private Gender gender;
  // 프로필 사진
  private String profileImageUrl;

  // 학생 프로필
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "student_profile_id")
  private StudentProfile studentProfile;

  // 선생님 프로필
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  // 소셜 로그인 유저
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "oauth2_user_id")
  private Oauth2User oauth2User;

}
