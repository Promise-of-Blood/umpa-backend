package promiseofblood.umpabackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.Review;

public class ServiceReviewDto {

  @Getter
  public static class ReviewRequest {

    @NotNull(message = "평점은 필수 항목입니다.")
    @DecimalMin(value = "0.5", message = "평점은 0.5 이상이어야 합니다.")
    private Double rating; // 0.5 단위로 나누어지는 값이어야 함

    private String content; // 리뷰 내용, 최소 5자 ~ 최대 500자

    private String reviewImageUrl1; // 리뷰 이미지 URL 1
    private String reviewImageUrl2; // 리뷰 이미지 URL 2
    private String reviewImageUrl3; // 리뷰 이미지 URL 3

  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class ReviewDto {

    private Long id;

    private Double rating;
    private String content;

    private String reviewImageUrl1;
    private String reviewImageUrl2;
    private String reviewImageUrl3;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewDto from(Review review) {
      return ReviewDto.builder()
        .id(review.getId())
        .rating(review.getRating())
        .content(review.getContent())
        .reviewImageUrl1(review.getReviewImageUrl1())
        .reviewImageUrl2(review.getReviewImageUrl2())
        .reviewImageUrl3(review.getReviewImageUrl3())
        .createdAt(review.getCreatedAt())
        .updatedAt(review.getUpdatedAt())
        .build();
    }
  }
}
