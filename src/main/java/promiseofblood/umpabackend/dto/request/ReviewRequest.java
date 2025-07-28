package promiseofblood.umpabackend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequest {

  @NotNull(message = "평점은 필수 항목입니다.")
  @DecimalMin(value = "0.5", message = "평점은 0.5 이상이어야 합니다.")
  private Double rating; // 0.5 단위로 나누어지는 값이어야 함

  private String content; // 리뷰 내용, 최소 5자 ~ 최대 500자

  private String reviewImageUrl1; // 리뷰 이미지 URL 1
  private String reviewImageUrl2; // 리뷰 이미지 URL 2
  private String reviewImageUrl3; // 리뷰 이미지 URL 3
  
}
