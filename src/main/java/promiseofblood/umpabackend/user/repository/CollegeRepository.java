package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.user.entitiy.College;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}
