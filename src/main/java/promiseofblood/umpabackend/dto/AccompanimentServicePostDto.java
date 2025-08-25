package promiseofblood.umpabackend.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Instrument;

@Getter
public class AccompanimentServicePostDto {

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class AccompanimentServicePostResponse {

    private String title;

    private String description;

    private String thumbnailImage;

    private TeacherProfileDto.TeacherProfileResponse teacherProfile;

    private String reviewRating;

    private String costPerUnit;

    private String additionalCostPolicy;

    private Instrument instrument;

    private int includedPracticeCount;

    private int additionalPracticeCost;

    private Boolean isMrIncluded;

    private String practiceLocation;

    private List<String> videoUrls;

    public static AccompanimentServicePostResponse from(
      AccompanimentServicePost accompanimentServicePost, User user) {

      return AccompanimentServicePostResponse.builder()
        .title(accompanimentServicePost.getTitle())
        .description(accompanimentServicePost.getDescription())
        .thumbnailImage(accompanimentServicePost.getThumbnailImageUrl())
        .teacherProfile(TeacherProfileDto.TeacherProfileResponse.from(user.getTeacherProfile()))
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

}
