package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.AccompanimentPracticeLocation;
import promiseofblood.umpabackend.domain.vo.Instrument;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RetrieveAccompanimentServicePostResponse {

  private String title;

  private String description;

  private String thumbnailImage;

  private RetrieveTeacherProfileResponse teacherProfile;

  private String reviewRating;

  private String costPerUnit;

  private String additionalCostPolicy;

  private Instrument instrument;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private Boolean isMrIncluded;

  private List<AccompanimentPracticeLocation> practiceLocations;

  private List<String> videoUrls;

  public static RetrieveAccompanimentServicePostResponse from(
      AccompanimentServicePost accompanimentServicePost, User user) {

    return RetrieveAccompanimentServicePostResponse.builder()
        .title(accompanimentServicePost.getTitle())
        .description(accompanimentServicePost.getDescription())
        .thumbnailImage(accompanimentServicePost.getThumbnailImageUrl())
        .teacherProfile(RetrieveTeacherProfileResponse.from(user.getTeacherProfile()))
        .reviewRating("0.0")
        .costPerUnit(accompanimentServicePost.getCostAndUnit())
        .additionalCostPolicy(accompanimentServicePost.getAdditionalCostPolicy())
        .instrument(accompanimentServicePost.getInstrument())
        .includedPracticeCount(accompanimentServicePost.getIncludedPracticeCount())
        .additionalPracticeCost(accompanimentServicePost.getAdditionalPracticeCost())
        .isMrIncluded(accompanimentServicePost.isMrIncluded())
        .practiceLocations(accompanimentServicePost.getPracticeLocations())
        .videoUrls(accompanimentServicePost.getVideoUrls())
        .build();
  }
}
