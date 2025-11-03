package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.PracticeLocation;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Entity
@DiscriminatorValue("ACCOMPANIMENT")
@Getter
@SuperBuilder
@Table(name = "accompaniment_service_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanimentServicePost extends ServicePost {

  @Embedded private ServiceCost serviceCost;

  private String additionalCostPolicy;

  @Enumerated(EnumType.STRING)
  private Instrument instrument;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private boolean isMrIncluded;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<PracticeLocation> practiceLocations;

  @ElementCollection private List<String> videoUrls;
}
