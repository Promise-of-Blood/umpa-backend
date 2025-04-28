package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

  // 전공종류, 지역, 수업방식
  @Enumerated(EnumType.STRING)
  private Major major;

  @Enumerated(EnumType.STRING)
  private LessonStyle lessonStyle;

  // 학생, 선생님 프로필
  private Long TeacherProfileId;
  private Long StudentProfileId;

  // 소셜 로그인 유저
  private Long oauth2UserId;
}
