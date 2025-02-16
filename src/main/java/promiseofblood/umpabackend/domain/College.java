package promiseofblood.umpabackend.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "colleges")
public class College extends IdEntity {

  private String name;
}
