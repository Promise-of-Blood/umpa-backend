package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.domain.vo.ArticleStatus;

@Entity
@Getter
@SuperBuilder
@Table(name = "articles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends TimeStampedEntity {

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ArticleStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @Column(nullable = false)
  private Long viewCount;

  @Column(nullable = false)
  private boolean isDeleted;

  public void updateArticle(String title, String content, ArticleStatus status) {
    if (title != null) {
      this.title = title;
    }
    if (content != null) {
      this.content = content;
    }
    if (status != null) {
      this.status = status;
    }
  }

  public void softDelete() {
    this.isDeleted = true;
  }

  public void incrementViewCount() {
    this.viewCount++;
  }
}
