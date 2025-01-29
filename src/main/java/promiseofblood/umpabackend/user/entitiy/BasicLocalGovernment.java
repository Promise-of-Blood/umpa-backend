package promiseofblood.umpabackend.user.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.common.entitiy.IdEntitiy;

@Entity
@Getter
@Table(name = "basic_local_governments") // 기초자치단체 (시군구)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicLocalGovernment extends IdEntitiy {

  @ManyToOne
  @JoinColumn(name = "regional_local_government_id")
  private RegionalLocalGovernment regionalLocalGovernment;

  private String name;
}
