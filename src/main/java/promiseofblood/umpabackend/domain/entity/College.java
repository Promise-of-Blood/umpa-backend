package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.abs.IdEntity;

@Entity
@Getter
@Table(name = "colleges")
public class College extends IdEntity {

  private String name;
}
