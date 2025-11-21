package kr.co.umpabackend.domain.repository;

import java.util.Optional;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

  boolean existsByUsername(Username username);
}
