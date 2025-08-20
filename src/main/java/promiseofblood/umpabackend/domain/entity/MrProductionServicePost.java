package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Entity
@DiscriminatorValue("MR_PRODUCTION")
@Getter
@Table(name = "mr_production_service_posts")
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MrProductionServicePost extends ServicePost {

  @Embedded
  private ServiceCost serviceCost;

  private String additionalCostPolicy;

  private int freeRevisionCount;

  @Embedded
  private DurationRange averageDuration;

  private String softwareUsed;

  @ElementCollection
  private List<SampleMrUrl> sampleMrUrls;

  @Override
  public String getCostAndUnit() {
    return serviceCost.getCost() + "원/" + serviceCost.getUnit();
  }
}
