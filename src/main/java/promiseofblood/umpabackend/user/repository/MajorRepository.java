package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.user.entitiy.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {}
