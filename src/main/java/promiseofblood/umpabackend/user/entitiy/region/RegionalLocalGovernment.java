package promiseofblood.umpabackend.user.entitiy.region;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import promiseofblood.umpabackend.common.entitiy.IdEntitiy;

import java.util.List;

@Entity
@Getter
@Table(name = "regional_local_governments") // 지역자치단체 (도, 광역시, 특별시, 특별자치시, 특별자치도)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionalLocalGovernment extends IdEntitiy {

  private String name;

  @OneToMany(mappedBy = "regionalLocalGovernment")
  private List<BasicLocalGovernment> basicLocalGovernments;
}
