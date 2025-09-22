package promiseofblood.umpabackend.web.schema.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

/** 서비스 단위당 비용 DTO */
@Getter
@AllArgsConstructor
public class ServiceCostResponse {

  private int cost;

  private String unit;

  public static ServiceCostResponse from(ServiceCost serviceCost) {
    return new ServiceCostResponse(serviceCost.getCost(), serviceCost.getUnit());
  }
}
