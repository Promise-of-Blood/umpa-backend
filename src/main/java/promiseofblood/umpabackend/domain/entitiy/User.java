package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Gender;


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

  // 학생, 선생님 프로필
  private Long TeacherProfileId;
  private Long StudentProfileId;

  // 소셜 로그인 유저
  private Long oauth2UserId;
}
