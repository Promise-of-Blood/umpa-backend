package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.common.entitiy.TimeStampedEntity;

/** 사용자 엔티티 */
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity {

  private String profileImageUrl;

  private String name;

  @OneToOne
  @JoinColumn(name = "major_id")
  private Major major;

  //  @OneToOne
  //  @JoinColumn(name = "area_id")
  //  private Area area;

  @OneToOne
  @JoinColumn(name = "user_type_id")
  private UserType userType;

  @OneToOne
  @JoinColumn(name = "college_id")
  private College college;
}
