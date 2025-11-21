package kr.co.umpabackend.application.service;

import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.ServicePost;
import kr.co.umpabackend.domain.repository.ServicePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServicePostManageService {

  private final ServicePostRepository servicePostRepository;

  /** 게시물 모집 중단 */
  @Transactional
  public void pauseServicePost(Long postId, String loginId) {
    ServicePost servicePost = getServicePostWithAuthorization(postId, loginId);
    servicePost.pause();
  }

  /** 게시물 모집 재개 */
  @Transactional
  public void publishServicePost(Long postId, String loginId) {
    ServicePost servicePost = getServicePostWithAuthorization(postId, loginId);
    servicePost.publish();
  }

  /** 게시물 삭제 (Soft Delete) */
  @Transactional
  public void deleteServicePost(Long postId, String loginId) {
    ServicePost servicePost = getServicePostWithAuthorization(postId, loginId);
    servicePost.delete();
  }

  /** 게시물 조회 및 작성자 권한 확인 */
  private ServicePost getServicePostWithAuthorization(Long postId, String loginId) {
    ServicePost servicePost =
        servicePostRepository
            .findByIdAndNotDeleted(postId)
            .orElseThrow(() -> new ResourceNotFoundException("게시물을 찾을 수 없습니다."));

    if (!servicePost.getUser().getLoginId().equals(loginId)) {
      throw new UnauthorizedException("게시물을 수정할 권한이 없습니다.");
    }

    return servicePost;
  }
}
