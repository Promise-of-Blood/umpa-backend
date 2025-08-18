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
@Getter
@SuperBuilder
@DiscriminatorValue("SCORE_PRODUCTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreProductionServicePost extends ServicePost {

  @ElementCollection
  private List<ServiceCost> serviceCosts;

  private int freeRevisionCount;

  private int additionalRevisionCost;

  private String additionalCostPolicy;

  @Embedded
  private DurationRange averageDuration;

  private String softwareUsed;

  private String sampleScoreImageUrl;

  @Override
  public String getCostAndUnit() {
    return "";
  }
}
