package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entitiy.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
