package promiseofblood.umpabackend.domain.entity;

import java.util.List;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SCORE_PRODUCTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreProductionPost extends ServicePost {

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
