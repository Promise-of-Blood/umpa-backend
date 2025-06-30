package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.vo.Instrument;

@Entity
@DiscriminatorValue("ACCOMPANIMENT")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanimentServicePost extends ServicePost {

  @Embedded
  private ServiceCost serviceCost;

  @Enumerated(EnumType.STRING)
  private Instrument instrument;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private boolean isMrIncluded;

  private String practiceLocation;

  @ElementCollection
  private List<String> videoUrls;


}
