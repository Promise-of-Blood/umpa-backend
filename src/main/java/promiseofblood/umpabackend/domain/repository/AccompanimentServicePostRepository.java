package promiseofblood.umpabackend.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;

public interface AccompanimentServicePostRepository
    extends JpaRepository<AccompanimentServicePost, Long> {

  @Query(
      "SELECT p FROM AccompanimentServicePost p "
          + "LEFT JOIN FETCH p.practiceLocations "
          + "LEFT JOIN FETCH p.user "
          + "WHERE p.id = :id")
  Optional<AccompanimentServicePost> findByIdWithPracticeLocations(@Param("id") Long id);

  @Query(
      "SELECT p FROM AccompanimentServicePost p "
          + "LEFT JOIN FETCH p.videoUrls "
          + "WHERE p.id = :id")
  Optional<AccompanimentServicePost> findByIdWithVideoUrls(@Param("id") Long id);

  @Query(
      "SELECT p FROM AccompanimentServicePost p " + "LEFT JOIN FETCH p.user " + "WHERE p.id = :id")
  Optional<AccompanimentServicePost> findByIdWithUser(@Param("id") Long id);
}
