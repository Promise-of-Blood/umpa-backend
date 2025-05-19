package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entitiy.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.Region;


@Entity
@Getter
@SuperBuilder
@Table(name = "teacher_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherProfile extends TimeStampedEntity {

  @Enumerated(EnumType.STRING)
  private Region lessonRegion;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherCareer> careers;

  @OneToMany(mappedBy = "teacherProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeacherLink> links;

}
