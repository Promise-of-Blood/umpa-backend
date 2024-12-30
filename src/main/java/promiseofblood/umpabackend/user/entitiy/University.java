package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "university")
public class University {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
