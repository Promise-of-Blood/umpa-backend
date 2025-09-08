package promiseofblood.umpabackend.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCost {

  private int cost;

  private String unit;

  public static ServiceCost of(int cost, String unit) {
    return ServiceCost.builder().cost(cost).unit(unit).build();
  }
}
