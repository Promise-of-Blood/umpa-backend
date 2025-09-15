package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.vo.AccompanimentPracticeLocation;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveAccompanimentServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  private String additionalCostPolicy;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private Boolean isMrIncluded;

  private Instrument instrument;

  private List<AccompanimentPracticeLocation> practiceLocations;

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
        .costPerUnit(CostPerUnitDto.from(post.getServiceCost()))
        // 합주 서비스 필드
        .additionalCostPolicy(post.getAdditionalCostPolicy())
        .instrument(post.getInstrument())
        .includedPracticeCount(post.getIncludedPracticeCount())
        .additionalPracticeCost(post.getAdditionalPracticeCost())
        .isMrIncluded(post.isMrIncluded())
        .practiceLocations(post.getPracticeLocations())
        .videoUrls(post.getVideoUrls())
        // 선생님 프로필, 리뷰
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
