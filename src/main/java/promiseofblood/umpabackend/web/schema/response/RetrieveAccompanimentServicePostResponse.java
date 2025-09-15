package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.vo.AccompanimentPracticeLocation;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveAccompanimentServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private String costPerUnit;

  private String additionalCostPolicy;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private Boolean isMrIncluded;

  private Instrument instrument;

  private List<AccompanimentPracticeLocation> practiceLocations;

  private List<String> videoUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private String reviewRating;

  public static RetrieveAccompanimentServicePostResponse from(AccompanimentServicePost post) {

    return RetrieveAccompanimentServicePostResponse.builder()
        .title(post.getTitle())
        .description(post.getDescription())
        .thumbnailImage(post.getThumbnailImageUrl())
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating("0.0")
        .costPerUnit(post.getCostAndUnit())
        .additionalCostPolicy(post.getAdditionalCostPolicy())
        .instrument(post.getInstrument())
        .includedPracticeCount(post.getIncludedPracticeCount())
        .additionalPracticeCost(post.getAdditionalPracticeCost())
        .isMrIncluded(post.isMrIncluded())
        .practiceLocations(post.getPracticeLocations())
        .videoUrls(post.getVideoUrls())
        .build();
  }
}
