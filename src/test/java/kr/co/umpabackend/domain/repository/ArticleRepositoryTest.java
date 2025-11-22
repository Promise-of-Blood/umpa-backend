package kr.co.umpabackend.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import kr.co.umpabackend.domain.entity.Article;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.vo.ArticleStatus;
import kr.co.umpabackend.domain.vo.Gender;
import kr.co.umpabackend.domain.vo.ProfileType;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ArticleRepositoryTest {

  @Autowired private ArticleRepository articleRepository;

  @Autowired private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser =
        User.register(
            "testuser",
            "password",
            Gender.MALE,
            UserStatus.ACTIVE,
            Role.USER,
            "테스트사용자",
            ProfileType.STUDENT,
            null);
    testUser = userRepository.save(testUser);
  }

  @Test
  @DisplayName("PUBLISHED 상태의 삭제되지 않은 아티클만 조회")
  void findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc() {
    // given
    Article publishedArticle =
        Article.builder()
            .title("Published Article")
            .content("Content")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    Article draftArticle =
        Article.builder()
            .title("Draft Article")
            .content("Content")
            .status(ArticleStatus.DRAFT)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    Article deletedArticle =
        Article.builder()
            .title("Deleted Article")
            .content("Content")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(true)
            .build();

    articleRepository.save(publishedArticle);
    articleRepository.save(draftArticle);
    articleRepository.save(deletedArticle);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<Article> result =
        articleRepository.findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
            ArticleStatus.PUBLISHED, pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(1);
    assertThat(result.getContent().get(0).getTitle()).isEqualTo("Published Article");
  }

  @Test
  @DisplayName("생성일시 내림차순 정렬 확인")
  void findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc_OrderByCreatedAtDesc() {
    // given
    Article article1 =
        Article.builder()
            .title("Article 1")
            .content("Content 1")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    Article article2 =
        Article.builder()
            .title("Article 2")
            .content("Content 2")
            .status(ArticleStatus.PUBLISHED)
            .author(testUser)
            .viewCount(0L)
            .isDeleted(false)
            .build();

    articleRepository.save(article1);
    articleRepository.save(article2);

    Pageable pageable = PageRequest.of(0, 10);

    // when
    Page<Article> result =
        articleRepository.findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
            ArticleStatus.PUBLISHED, pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(2);
    // 최신 순이므로 article2가 먼저 나와야 함
    assertThat(result.getContent().get(0).getTitle()).isEqualTo("Article 2");
    assertThat(result.getContent().get(1).getTitle()).isEqualTo("Article 1");
  }
}
