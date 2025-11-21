package promiseofblood.umpabackend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entity.Article;
import promiseofblood.umpabackend.domain.vo.ArticleStatus;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

  Page<Article> findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
      ArticleStatus status, Pageable pageable);
}
