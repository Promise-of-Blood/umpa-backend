package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.*;

import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "majors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
