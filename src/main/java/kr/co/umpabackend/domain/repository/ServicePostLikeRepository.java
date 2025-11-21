package kr.co.umpabackend.domain.repository;

import java.util.Optional;
import kr.co.umpabackend.domain.entity.ServicePost;
import kr.co.umpabackend.domain.entity.ServicePostLike;
import kr.co.umpabackend.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServicePostLikeRepository extends JpaRepository<ServicePostLike, Long> {

  Optional<ServicePostLike> findByUserAndServicePost(User user, ServicePost servicePost);

  boolean existsByUserAndServicePost(User user, ServicePost servicePost);

  @Query(
      """
      SELECT spl FROM ServicePostLike spl
      JOIN FETCH spl.servicePost sp
      JOIN FETCH spl.user u
      WHERE u.loginId = :loginId
      AND TYPE(sp) = :serviceType
      ORDER BY spl.createdAt DESC
      """)
  Page<ServicePostLike> findByUserLoginIdAndServiceType(
      @Param("loginId") String loginId,
      @Param("serviceType") Class<? extends ServicePost> serviceType,
      Pageable pageable);
}
