package kr.co.umpabackend.domain.repository;

import kr.co.umpabackend.domain.entity.Article;
import kr.co.umpabackend.domain.vo.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

  Page<Article> findAllByIsDeletedFalseAndStatusOrderByCreatedAtDesc(
      ArticleStatus status, Pageable pageable);
}
