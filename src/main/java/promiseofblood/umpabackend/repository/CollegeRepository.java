package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.College;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}
