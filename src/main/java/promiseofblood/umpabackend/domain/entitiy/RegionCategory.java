package promiseofblood.umpabackend.domain.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import promiseofblood.umpabackend.domain.entitiy.abs.IdEntity;

@Entity
@Getter
@Table(name = "region_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionCategory extends IdEntity {

  private String name;

  @OneToMany(mappedBy = "regionalLocalGovernment")
  private List<Region> regions;
}
