package promiseofblood.umpabackend.dto.request;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.DurationUnit;

@Getter
@AllArgsConstructor
public class MrProductionRegisterRequest {

  private String title;

  private String description;

  // 제작 비용
  private int cost;

  // 추가비용정책
  private String additionalCostPolicy;

  private int freeRevisionCount;

  private int minDurationValue;
  private DurationUnit minDurationUnit;

  private int maxDurationValue;
  private DurationUnit maxDurationUnit;

  private String softwareUsed;

  private List<String> sampleMrUrls;
}
