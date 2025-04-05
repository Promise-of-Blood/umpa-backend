package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entitiy.abs.IdEntity;

@Entity
@Getter
@Table(name = "colleges")
public class College extends IdEntity {

  private String name;
}
