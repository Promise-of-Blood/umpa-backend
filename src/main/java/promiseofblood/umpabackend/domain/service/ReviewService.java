package promiseofblood.umpabackend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.entity.Review;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.dto.ServiceReviewDto;
import promiseofblood.umpabackend.repository.ReviewRepository;
import promiseofblood.umpabackend.repository.ServicePostRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ServicePostRepository servicePostRepository;
  private final ReviewRepository reviewRepository;

  public Review createReview(Long servicePostId, ServiceReviewDto.ReviewRequest reviewRequest) {
    ServicePost servicePost = servicePostRepository.findById(servicePostId).orElse(null);

    Review review = Review.builder()
      .servicePost(servicePost)
      .rating(reviewRequest.getRating())
      .content(reviewRequest.getContent())
      .reviewImageUrl1(reviewRequest.getReviewImageUrl1())
      .reviewImageUrl2(reviewRequest.getReviewImageUrl2())
      .reviewImageUrl3(reviewRequest.getReviewImageUrl3())
      .build();

    reviewRepository.save(review);
    return review;
  }

}
