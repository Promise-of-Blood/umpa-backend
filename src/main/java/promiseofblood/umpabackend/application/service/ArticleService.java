package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.Article;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.ArticleRepository;
import promiseofblood.umpabackend.domain.vo.ArticleStatus;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.web.schema.request.CreateArticleRequest;
import promiseofblood.umpabackend.web.schema.request.UpdateArticleRequest;
import promiseofblood.umpabackend.web.schema.response.ListArticleResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveArticleResponse;

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
