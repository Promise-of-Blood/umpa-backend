package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.dto.TeacherProfileDto.TeacherProfileResponse;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AccompanimentServicePostDetailResponse {

  private String title;

  private String description;

  private String thumbnailImage;

  private TeacherProfileResponse teacherProfile;

  private String reviewRating;

  private String costPerUnit;

  private String additionalCostPolicy;

  private Instrument instrument;

  private int includedPracticeCount;

  private int additionalPracticeCost;

  private Boolean isMrIncluded;

  private String practiceLocation;

  private List<String> videoUrls;

  public static AccompanimentServicePostDetailResponse from(
    AccompanimentServicePost accompanimentServicePost, User user) {

    return AccompanimentServicePostDetailResponse.builder()
      .title(accompanimentServicePost.getTitle())
      .description(accompanimentServicePost.getDescription())
      .thumbnailImage(accompanimentServicePost.getThumbnailImageUrl())
      .teacherProfile(TeacherProfileResponse.from(user.getTeacherProfile()))
      .reviewRating("0.0")
      .costPerUnit(accompanimentServicePost.getCostAndUnit())
      .additionalCostPolicy(accompanimentServicePost.getAdditionalCostPolicy())
      .instrument(accompanimentServicePost.getInstrument())
      .includedPracticeCount(accompanimentServicePost.getIncludedPracticeCount())
      .additionalPracticeCost(accompanimentServicePost.getAdditionalPracticeCost())
      .isMrIncluded(accompanimentServicePost.isMrIncluded())
      .practiceLocation(accompanimentServicePost.getPracticeLocation())
      .videoUrls(accompanimentServicePost.getVideoUrls())
      .build();
  }
}
