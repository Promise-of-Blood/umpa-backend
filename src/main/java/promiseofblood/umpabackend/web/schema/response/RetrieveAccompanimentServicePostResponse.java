package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.PracticeLocation;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveAccompanimentServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse costPerUnit;

  private String additionalCostPolicy;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private Boolean isMrIncluded;

  private ConstantResponse<Instrument> instrument;

  private List<ConstantResponse<PracticeLocation>> practiceLocations;

  private List<String> videoUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveAccompanimentServicePostResponse from(AccompanimentServicePost post) {

    return RetrieveAccompanimentServicePostResponse.builder()
        // 공통 필드
        .id(post.getId())
        .thumbnailImage(post.getThumbnailImageUrl())
        .title(post.getTitle())
        .description(post.getDescription())
        .costPerUnit(ServiceCostResponse.from(post.getServiceCost()))
        // 합주 서비스 필드
        .additionalCostPolicy(post.getAdditionalCostPolicy())
        .instrument(new ConstantResponse<>(post.getInstrument()))
        .includedPracticeCount(post.getIncludedPracticeCount())
        .additionalPracticeCost(post.getAdditionalPracticeCost())
        .isMrIncluded(post.isMrIncluded())
        .practiceLocations(post.getPracticeLocations().stream().map(ConstantResponse::new).toList())
        .videoUrls(post.getVideoUrls())
        // 선생님 프로필, 리뷰
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
