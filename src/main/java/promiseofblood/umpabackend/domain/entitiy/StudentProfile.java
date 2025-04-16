package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;

@Entity
@Getter
@SuperBuilder
@Table(name = "student_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentProfile extends TimeStampedEntity {

}
