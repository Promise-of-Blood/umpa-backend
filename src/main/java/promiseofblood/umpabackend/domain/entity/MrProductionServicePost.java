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

  @Embedded private ServiceCost serviceCost;

  private String additionalCostPolicy;

  private int freeRevisionCount;

  private int additionalRevisionCost;

  @Embedded private DurationRange averageDuration;

  private List<String> usingSoftwareList;

  @ElementCollection private List<SampleMrUrl> sampleMrUrls;

  public static MrProductionServicePost create(
      // 공통 필드
      User user,
      String thumbnailImageUrl,
      String title,
      String description,
      // MR 제작 전용 필드
      ServiceCost serviceCost,
      String additionalCostPolicy,
      int freeRevisionCount,
      int additionalRevisionCost,
      DurationRange averageDuration,
      List<String> usingSoftwareList,
      List<SampleMrUrl> sampleMrUrls) {
    return MrProductionServicePost.builder()
        .thumbnailImageUrl(thumbnailImageUrl)
        .title(title)
        .description(description)
        .user(user)
        .serviceCost(serviceCost)
        .additionalCostPolicy(additionalCostPolicy)
        .freeRevisionCount(freeRevisionCount)
        .additionalRevisionCost(additionalRevisionCost)
        .averageDuration(averageDuration)
        .usingSoftwareList(usingSoftwareList)
        .sampleMrUrls(sampleMrUrls)
        .build();
  }
}
