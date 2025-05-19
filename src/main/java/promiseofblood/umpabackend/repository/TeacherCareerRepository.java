package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entitiy.TeacherCareer;

@Repository
public interface TeacherCareerRepository extends JpaRepository<TeacherCareer, Long> {

}
