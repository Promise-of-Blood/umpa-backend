package kr.co.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import kr.co.umpabackend.application.service.LessonService;
import kr.co.umpabackend.application.service.ServicePostLikeService;
import kr.co.umpabackend.application.service.ServicePostManageService;
import kr.co.umpabackend.infrastructure.security.SecurityUserDetails;
import kr.co.umpabackend.web.schema.request.CreateLessonServicePostRequest;
import kr.co.umpabackend.web.schema.response.ApiResponse.PaginatedResponse;
import kr.co.umpabackend.web.schema.response.ListLessonServicePostResponse;
import kr.co.umpabackend.web.schema.response.RetrieveLessonServicePostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Tag(name = "서비스 관리 API(레슨)")
public class LessonServiceController {

  private final LessonService lessonService;
  private final ServicePostManageService servicePostManageService;
  private final ServicePostLikeService servicePostLikeService;

  @Tag(name = "서비스 관리 API(레슨)")
  @PostMapping(value = "/lesson", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveLessonServicePostResponse> registerLesson(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @Validated @ModelAttribute CreateLessonServicePostRequest request) {

    var post = lessonService.createLessonServicePost(request, securityUserDetails.getLoginId());

    return ResponseEntity.ok(post);
  }

  @GetMapping("/lesson")
  public ResponseEntity<PaginatedResponse<ListLessonServicePostResponse>> getAllLessonServices(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 0보다 커야 합니다.") int size) {

    Page<ListLessonServicePostResponse> servicePostResponsePage =
        this.lessonService.getAllServices(page, size);

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @GetMapping(path = "/lesson/{id}")
  public ResponseEntity<RetrieveLessonServicePostResponse> retrieveLessonServicePost(
      @PathVariable Long id) {

    var lessonServicePost = lessonService.retrieveLessonServicePost(id);

    return ResponseEntity.ok(lessonServicePost);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping(path = "/{id}/likes")
  public ResponseEntity<Void> addLike(
      @PathVariable Long id, @AuthenticationPrincipal SecurityUserDetails userDetails) {

    servicePostLikeService.likeServicePost(userDetails.getUsername(), id);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping(path = "/{id}/likes")
  public ResponseEntity<Void> unlikeServicePost(
      @AuthenticationPrincipal SecurityUserDetails userDetails, @PathVariable Long id) {

    servicePostLikeService.unlikeServicePost(userDetails.getUsername(), id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping(path = "/{id}/pause")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> pauseServicePost(
      @PathVariable Long id, @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    servicePostManageService.pauseServicePost(id, securityUserDetails.getUsername());
    return ResponseEntity.ok().build();
  }

  @PatchMapping(path = "/{id}/publish")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> publishServicePost(
      @PathVariable Long id, @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    servicePostManageService.publishServicePost(id, securityUserDetails.getUsername());
    return ResponseEntity.ok().build();
  }
}
