package kr.co.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.application.service.ArticleService;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.infrastructure.security.SecurityUserDetails;
import kr.co.umpabackend.web.schema.request.CreateArticleRequest;
import kr.co.umpabackend.web.schema.request.UpdateArticleRequest;
import kr.co.umpabackend.web.schema.response.ListArticleResponse;
import kr.co.umpabackend.web.schema.response.RetrieveArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@Tag(name = "아티클 API", description = "아티클 관리 API")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;
  private final UserRepository userRepository;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "아티클 생성", description = "새로운 아티클을 생성합니다. (ADMIN 권한 필요)")
  public ResponseEntity<RetrieveArticleResponse> createArticle(
      @Valid @RequestBody CreateArticleRequest request,
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    User user = getUserFromSecurityDetails(securityUserDetails);
    RetrieveArticleResponse response = articleService.createArticle(request, user);

    return ResponseEntity.status(201).body(response);
  }

  @PatchMapping("/{articleId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "아티클 수정", description = "기존 아티클을 수정합니다. (ADMIN 권한 필요)")
  public ResponseEntity<RetrieveArticleResponse> updateArticle(
      @PathVariable Long articleId,
      @RequestBody UpdateArticleRequest request,
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    User user = getUserFromSecurityDetails(securityUserDetails);
    RetrieveArticleResponse response = articleService.updateArticle(articleId, request, user);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{articleId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "아티클 삭제", description = "아티클을 삭제합니다. (ADMIN 권한 필요)")
  public ResponseEntity<Void> deleteArticle(
      @PathVariable Long articleId,
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    User user = getUserFromSecurityDetails(securityUserDetails);
    articleService.deleteArticle(articleId, user);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{articleId}")
  @Operation(summary = "아티클 상세 조회", description = "아티클 상세 정보를 조회합니다. (전체 허용)")
  public ResponseEntity<RetrieveArticleResponse> getArticle(@PathVariable Long articleId) {

    RetrieveArticleResponse response = articleService.getArticle(articleId);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(summary = "아티클 목록 조회", description = "발행된 아티클 목록을 페이징하여 조회합니다. (전체 허용)")
  public ResponseEntity<Page<ListArticleResponse>> getArticles(
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
          Pageable pageable) {

    Page<ListArticleResponse> response = articleService.getArticles(pageable);

    return ResponseEntity.ok(response);
  }

  private User getUserFromSecurityDetails(SecurityUserDetails securityUserDetails) {
    return userRepository
        .findByLoginId(securityUserDetails.getLoginId())
        .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
  }
}
