package promiseofblood.umpabackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "regional_local_governments") // 지역자치단체 (도, 광역시, 특별시, 특별자치시, 특별자치도)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionalLocalGovernment extends IdEntity {

  private String name;

  @OneToMany(mappedBy = "regionalLocalGovernment")
  private List<BasicLocalGovernment> basicLocalGovernments;
}
