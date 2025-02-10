package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import promiseofblood.umpabackend.common.entitiy.IdEntity;

@Entity
@Getter
@Table(name = "colleges")
public class College extends IdEntity {

  private String name;
}
