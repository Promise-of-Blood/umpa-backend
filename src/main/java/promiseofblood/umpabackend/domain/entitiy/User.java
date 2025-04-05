package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;


@Entity
@Getter
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampedEntity {

  private String username;

}
