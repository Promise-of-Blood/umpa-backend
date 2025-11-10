package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.ServicePostLike;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.ServicePostLikeRepository;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicePostLikeService {

  private final ServicePostLikeRepository servicePostLikeRepository;
  private final UserRepository userRepository;
  private final ServicePostRepository servicePostRepository;

  @Transactional(readOnly = true)
  public <T extends ServicePost> Page<T> getLikedServicePostsByType(
      String loginId, Class<T> serviceType, Pageable pageable) {

    Page<ServicePostLike> likes =
        servicePostLikeRepository.findByUserLoginIdAndServiceType(loginId, serviceType, pageable);

    return likes.map(
        like -> {
          ServicePost post = like.getServicePost();

          if (!serviceType.isInstance(post)) {
            throw new IllegalStateException(
                String.format(
                    "예상치 못한 타입입니다. 예상: %s, 실제: %s",
                    serviceType.getSimpleName(), post.getClass().getSimpleName()));
          }

          return serviceType.cast(post);
        });
  }

  public void likeServicePost(String loginId, Long postId) {
    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("..."));
    ServicePost servicePost =
        servicePostRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("..."));

    if (!servicePostLikeRepository.existsByUserAndServicePost(user, servicePost)) {
      ServicePostLike servicePostLike =
          ServicePostLike.builder().user(user).servicePost(servicePost).build();
      servicePostLikeRepository.save(servicePostLike);
    }
  }

  public void unlikeServicePost(String loginId, Long postId) {
    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("..."));
    ServicePost servicePost =
        servicePostRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("..."));

    servicePostLikeRepository
        .findByUserAndServicePost(user, servicePost)
        .ifPresent(servicePostLikeRepository::delete);
  }
}
