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

  @OneToOne
  @JoinColumn(name = "major_id")
  private Major major;

  @OneToOne
  @JoinColumn(name = "user_type_id")
  private UserType userType;

  @OneToOne
  @JoinColumn(name = "college_id")
  private College college;
  
}
