package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeStampedEntity {

  private Double rating; // 0.5 단위로 나누어지는 값이어야 함

  // 최소 5자 ~ 최대 500자
  @Column(length = 500)
  private String content; // 리뷰 내용

  private String reviewImageUrl1;
  private String reviewImageUrl2;
  private String reviewImageUrl3;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_post_id", nullable = false)
  private ServicePost servicePost; // 리뷰가 작성된 서비스 게시글
}
