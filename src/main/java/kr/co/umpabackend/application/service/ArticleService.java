package kr.co.umpabackend.application.service;

import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.Article;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.ArticleRepository;
import kr.co.umpabackend.domain.vo.ArticleStatus;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.web.schema.request.CreateArticleRequest;
import kr.co.umpabackend.web.schema.request.UpdateArticleRequest;
import kr.co.umpabackend.web.schema.response.ListArticleResponse;
import kr.co.umpabackend.web.schema.response.RetrieveArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  @Transactional
  public RetrieveArticleResponse createArticle(CreateArticleRequest request, User user) {
    Article article =
        Article.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .status(ArticleStatus.DRAFT)
            .author(user)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    Article saved = articleRepository.save(article);
    log.info(
        "Article created: id={}, title={}, author={}",
        saved.getId(),
        saved.getTitle(),
        user.getLoginId());

    return toDetailResponse(saved);
  }

  @Transactional
  public RetrieveArticleResponse updateArticle(
      Long articleId, UpdateArticleRequest request, User user) {
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new ResourceNotFoundException("아티클을 찾을 수 없습니다."));

    if (article.isDeleted()) {
      throw new ResourceNotFoundException("삭제된 아티클입니다.");
    }

    validateAuthorization(article, user);

    article.updateArticle(request.getTitle(), request.getContent(), request.getStatus());
    log.info("Article updated: id={}, title={}", article.getId(), article.getTitle());

    return toDetailResponse(article);
  }

  @Transactional
  public void deleteArticle(Long articleId, User user) {
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new ResourceNotFoundException("아티클을 찾을 수 없습니다."));

    if (article.isDeleted()) {
      throw new ResourceNotFoundException("이미 삭제된 아티클입니다.");
    }

    validateAuthorization(article, user);

    article.softDelete();
    log.info("Article soft deleted: id={}", article.getId());
  }

  @Transactional
  public RetrieveArticleResponse getArticle(Long articleId) {
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new ResourceNotFoundException("아티클을 찾을 수 없습니다."));

    if (article.isDeleted()) {
      throw new ResourceNotFoundException("삭제된 아티클입니다.");
    }

    if (article.getStatus() != ArticleStatus.PUBLISHED) {
      throw new ResourceNotFoundException("발행되지 않은 아티클입니다.");
    }

    article.incrementViewCount();
    log.info("Article viewed: id={}, viewCount={}", article.getId(), article.getViewCount());

    return toDetailResponse(article);
  }

  @Transactional(readOnly = true)
  public Page<ListArticleResponse> getArticles(Pageable pageable) {
    Page<Article> articles =
        articleRepository.findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
            ArticleStatus.PUBLISHED, pageable);

    return articles.map(this::toSummaryResponse);
  }

  private void validateAuthorization(Article article, User user) {
    boolean isAuthor = article.getAuthor().getId().equals(user.getId());
    boolean isAdmin = user.getRole() == Role.ADMIN;

    if (!isAuthor && !isAdmin) {
      throw new UnauthorizedException("권한이 없습니다.");
    }
  }

  private RetrieveArticleResponse toDetailResponse(Article article) {
    return new RetrieveArticleResponse(
        article.getId(),
        article.getTitle(),
        article.getContent(),
        article.getStatus(),
        article.getAuthor().getUsername().getValue(),
        article.getViewCount(),
        article.getCreatedAt());
  }

  private ListArticleResponse toSummaryResponse(Article article) {
    return new ListArticleResponse(
        article.getId(),
        article.getTitle(),
        article.getAuthor().getUsername().getValue(),
        article.getViewCount(),
        article.getCreatedAt());
  }
}
