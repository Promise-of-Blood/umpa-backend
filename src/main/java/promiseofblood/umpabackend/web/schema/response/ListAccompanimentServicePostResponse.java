package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.PracticeLocation;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ListAccompanimentServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private ConstantResponse<Instrument> instrument;

  private List<ConstantResponse<PracticeLocation>> practiceLocations;

  private String teacherName;

  private float reviewRating;

  public static ListAccompanimentServicePostResponse from(AccompanimentServicePost post) {

    return ListAccompanimentServicePostResponse.builder()
        // 공통 필드
        .id(post.getId())
        .thumbnailImage(post.getThumbnailImageUrl())
        .title(post.getTitle())
        .description(post.getDescription())
        .serviceCost(ServiceCostResponse.from(post.getServiceCost()))
        // 합주 서비스 필드
        .instrument(new ConstantResponse<>(post.getInstrument()))
        .practiceLocations(post.getPracticeLocations().stream().map(ConstantResponse::new).toList())
        // 선생님 프로필, 리뷰
        .teacherName(String.valueOf((post.getUser().getUsername())))
        .reviewRating(0.0f)
        .build();
  }
}
