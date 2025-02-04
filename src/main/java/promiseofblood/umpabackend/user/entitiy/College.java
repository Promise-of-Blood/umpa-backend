package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "colleges")
public class College {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
