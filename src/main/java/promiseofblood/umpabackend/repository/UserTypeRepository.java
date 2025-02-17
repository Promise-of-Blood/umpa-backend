package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.UserType;


@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
  UserType getByName(String name);
}
