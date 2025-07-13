package promiseofblood.umpabackend.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServicePostResponse {

  private Long id;

  private String title;

  private List<String> tags;

  private String teacherName;

  private String thumbnailImageUrl;

  private String costAndUnit;

  private Float reviewRating;

}
