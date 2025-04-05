package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;

import lombok.*;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.domain.entitiy.abs.IdEntity;

@Entity
@Getter
@Table(name = "majors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major extends IdEntity {

  private String name;
}
