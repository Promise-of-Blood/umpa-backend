package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.Review;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.repository.ReviewRepository;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.web.schema.request.CreateServiceReviewRequest;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ServicePostRepository servicePostRepository;
  private final ReviewRepository reviewRepository;

  @Transactional
  public Review createReview(Long servicePostId, CreateServiceReviewRequest reviewRequest) {
    ServicePost servicePost = servicePostRepository.findById(servicePostId).orElse(null);

    Review review =
        Review.builder()
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
