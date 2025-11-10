package promiseofblood.umpabackend.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.User;

@Repository
public interface ServicePostRepository extends JpaRepository<ServicePost, Long> {

  Page<ServicePost> findAllByServiceType(String serviceType, Pageable pageable);

  /** 삭제되지 않은 게시물만 조회 (페이징) */
  Page<ServicePost> findAllByServiceTypeAndDeletedAtIsNull(String serviceType, Pageable pageable);

  /** 특정 사용자의 삭제되지 않은 게시물 조회 */
  List<ServicePost> findAllByUserAndDeletedAtIsNull(User user);

  /** 특정 사용자의 모든 게시물 조회 (삭제된 것 포함) */
  List<ServicePost> findAllByUser(User user);

  /** ID로 삭제되지 않은 게시물 조회 */
  @Query("SELECT sp FROM ServicePost sp WHERE sp.id = :id AND sp.deletedAt IS NULL")
  java.util.Optional<ServicePost> findByIdAndNotDeleted(@Param("id") Long id);
}
