package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.domain.entitiy.abs.IdEntity;

@Entity
@Getter
@Table(name = "regions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region extends IdEntity {

  private Long regionCategoryId;

  private String name;
}
