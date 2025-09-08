package promiseofblood.umpabackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import promiseofblood.umpabackend.domain.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {}
