package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.user.entitiy.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
