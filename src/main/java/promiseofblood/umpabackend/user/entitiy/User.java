package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.common.entitiy.TimeStampedEntity;


@Entity
@Getter
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity {

  private String name;

  private String profileImageUrl;

  // 한 줄 소개글
  private String oneLineDescription;

  // 소개글 - 선생님 회원일 때에만 사용
  private String fullDescription;

  // 선생님 회원일 때에만 사용
  private String url;

  // 전공 과목 - 선생님 회원일 때에만 사용
  @OneToOne
  @JoinColumn(name = "major_id")
  private Major major;

  @OneToOne
  @JoinColumn(name = "college_id")
  private College college;

  @OneToOne
  @JoinColumn(name = "user_type_id")
  private UserType userType;

}
