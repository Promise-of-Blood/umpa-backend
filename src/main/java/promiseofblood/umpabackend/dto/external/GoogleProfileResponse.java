package promiseofblood.umpabackend.dto.external;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GoogleProfileResponse {

  private String sub;

  private String name;

  @JsonProperty("given_name")
  private String givenName;

  private String picture;
}
