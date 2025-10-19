package promiseofblood.umpabackend.dto;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.Review;

public class ServiceReviewDto {

  @Getter
  @Builder
  public static class ReviewResponse {

    private Long id;

    private Double rating;

    private String content;

    private String reviewImageUrl1;

    private String reviewImageUrl2;

    private String reviewImageUrl3;

    public static ReviewResponse from(Review review) {
      ReviewResponse response =
          ReviewResponse.builder()
              .id(review.getId())
              .rating(review.getRating())
              .content(review.getContent())
              .reviewImageUrl1(review.getReviewImageUrl1())
              .reviewImageUrl2(review.getReviewImageUrl2())
              .reviewImageUrl3(review.getReviewImageUrl3())
              .build();

      return response;
    }
  }
}
