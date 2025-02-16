package promiseofblood.umpabackend.domain;

import jakarta.persistence.*;

import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "majors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major extends IdEntity {

  private String name;
}
