package promiseofblood.umpabackend.web.schema.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.Review;

@Getter
@Builder
public class ListServiceReviewResponse {

  private Long id;

  private Double rating;

  private String content;

  private String reviewImageUrl1;

  private String reviewImageUrl2;

  private String reviewImageUrl3;

  public static ListServiceReviewResponse from(Review review) {
    ListServiceReviewResponse response =
        ListServiceReviewResponse.builder()
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
