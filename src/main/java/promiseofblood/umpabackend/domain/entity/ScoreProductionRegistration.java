package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SCORE_PRODUCTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreProductionRegistration extends ServicePost {

  @ElementCollection
  private List<ServiceCost> serviceCosts;

  private int freeRevisionCount;

  @Embedded
  private DurationRange averageDuration;

  private String softwareUsed;

  private String sampleScoreUrl;

  @Override
  public String getCostAndUnit() {
    return "";
  }
}

