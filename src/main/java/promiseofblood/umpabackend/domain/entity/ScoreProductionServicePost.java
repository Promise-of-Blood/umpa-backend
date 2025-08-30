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
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Entity
@Getter
@SuperBuilder
@Table(name = "score_production_service_posts")
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

  private List<String> usingSoftwareList;

  private String sampleScoreImageUrl;

  @Override
  public String getCostAndUnit() {
    return "";
  }
}
