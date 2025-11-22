package kr.co.umpabackend.web.schema.response;

import kr.co.umpabackend.domain.vo.ServiceCost;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
