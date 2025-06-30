package promiseofblood.umpabackend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceResponse {

  private Long id;

  private String title;

  @JsonIgnore
  private int cost;

  @JsonIgnore
  private String unit;

  private Float reviewRating;
  
  @JsonProperty("cost")
  public String getCost() {
    return String.format("%,d원/%s", cost, unit);
  }

}
