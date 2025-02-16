package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
