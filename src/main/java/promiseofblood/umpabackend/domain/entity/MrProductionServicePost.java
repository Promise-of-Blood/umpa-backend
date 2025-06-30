package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@DiscriminatorValue("MR_PRODUCTION")
@Getter
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
  private List<String> sampleMrUrls;

  @Override
  public String getCostAndUnit() {
    return serviceCost.getCost() + " " + serviceCost.getUnit();
  }
}
