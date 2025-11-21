package kr.co.umpabackend.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.Article;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.ArticleRepository;
import kr.co.umpabackend.domain.vo.ArticleStatus;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.Username;
import kr.co.umpabackend.web.schema.request.CreateArticleRequest;
import kr.co.umpabackend.web.schema.request.UpdateArticleRequest;
import kr.co.umpabackend.web.schema.response.ListArticleResponse;
import kr.co.umpabackend.web.schema.response.RetrieveArticleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

  @Mock private ArticleRepository articleRepository;

  @InjectMocks private ArticleService articleService;

  private User testUser;
  private User adminUser;
  private Article testArticle;

  @BeforeEach
  void setUp() {
    testUser =
        User.builder()
            .id(1L)
            .loginId("testuser")
            .username(new Username("테스트사용자"))
            .role(Role.USER)
            .build();

    adminUser =
        User.builder()
            .id(2L)
            .loginId("admin")
            .username(new Username("관리자"))
            .role(Role.ADMIN)
            .build();

    testArticle =
        Article.builder()
            .id(1L)
            .title("Test Article")
            .content("Test Content")
            .status(ArticleStatus.DRAFT)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();
  }

  @Test
  @DisplayName("아티클 생성 - 성공")
  void createArticle_Success() {
    // given
    CreateArticleRequest request = new CreateArticleRequest("New Article", "New Content");
    Article savedArticle =
        Article.builder()
            .id(1L)
            .title(request.getTitle())
            .content(request.getContent())
            .status(ArticleStatus.DRAFT)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    when(articleRepository.save(any(Article.class))).thenReturn(savedArticle);

    // when
    RetrieveArticleResponse response = articleService.createArticle(request, testUser);

    // then
    assertThat(response.getTitle()).isEqualTo("New Article");
    assertThat(response.getContent()).isEqualTo("New Content");
    assertThat(response.getStatus()).isEqualTo(ArticleStatus.DRAFT);
    assertThat(response.getViewCount()).isZero();
    verify(articleRepository).save(any(Article.class));
  }

  @Test
  @DisplayName("아티클 수정 - 작성자가 수정 성공")
  void updateArticle_ByAuthor_Success() {
    // given
    UpdateArticleRequest request =
        new UpdateArticleRequest("Updated Title", "Updated Content", ArticleStatus.PUBLISHED);
    when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

    // when
    RetrieveArticleResponse response = articleService.updateArticle(1L, request, testUser);

    // then
    assertThat(response.getTitle()).isEqualTo("Updated Title");
    assertThat(response.getContent()).isEqualTo("Updated Content");
    assertThat(response.getStatus()).isEqualTo(ArticleStatus.PUBLISHED);
  }

  @Test
  @DisplayName("아티클 수정 - 관리자가 수정 성공")
  void updateArticle_ByAdmin_Success() {
    // given
    UpdateArticleRequest request =
        new UpdateArticleRequest("Admin Updated", null, ArticleStatus.PUBLISHED);
    when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

    // when
    RetrieveArticleResponse response = articleService.updateArticle(1L, request, adminUser);

    // then
    assertThat(response.getTitle()).isEqualTo("Admin Updated");
  }

  @Test
  @DisplayName("아티클 수정 - 권한 없음 실패")
  void updateArticle_Unauthorized_Fail() {
    // given
    User otherUser =
        User.builder()
            .id(3L)
            .loginId("other")
            .username(new Username("다른사용자"))
            .role(Role.USER)
            .build();
    UpdateArticleRequest request = new UpdateArticleRequest("Title", "Content", null);
    when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

    // when & then
    assertThatThrownBy(() -> articleService.updateArticle(1L, request, otherUser))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("권한이 없습니다.");
  }

  @Test
  @DisplayName("아티클 삭제 - 성공")
  void deleteArticle_Success() {
    // given
    when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

    // when
    articleService.deleteArticle(1L, testUser);

    // then
    assertThat(testArticle.isDeleted()).isTrue();
  }

  @Test
  @DisplayName("아티클 조회 - 조회수 증가")
  void getArticle_IncrementViewCount() {
    // given
    Article publishedArticle =
        Article.builder()
            .id(1L)
            .title("Published Article")
            .content("Content")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(5L)
            .isDeleted(false)
            .build();
    when(articleRepository.findById(1L)).thenReturn(Optional.of(publishedArticle));

    // when
    RetrieveArticleResponse response = articleService.getArticle(1L);

    // then
    assertThat(response.getViewCount()).isEqualTo(6L);
  }

  @Test
  @DisplayName("아티클 조회 - PUBLISHED가 아니면 실패")
  void getArticle_NotPublished_Fail() {
    // given
    when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle)); // DRAFT status

    // when & then
    assertThatThrownBy(() -> articleService.getArticle(1L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("발행되지 않은 아티클입니다.");
  }

  @Test
  @DisplayName("아티클 목록 조회 - 페이징")
  void getArticles_Pagination_Success() {
    // given
    Article article1 =
        Article.builder()
            .id(1L)
            .title("Article 1")
            .content("Content 1")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(10L)
            .isDeleted(false)
            .build();
    Page<Article> articlePage = new PageImpl<>(java.util.List.of(article1));
    Pageable pageable = PageRequest.of(0, 10);
    when(articleRepository.findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
            ArticleStatus.PUBLISHED, pageable))
        .thenReturn(articlePage);

    // when
    Page<ListArticleResponse> response = articleService.getArticles(pageable);

    // then
    assertThat(response.getTotalElements()).isEqualTo(1);
    assertThat(response.getContent().get(0).getTitle()).isEqualTo("Article 1");
  }
}
