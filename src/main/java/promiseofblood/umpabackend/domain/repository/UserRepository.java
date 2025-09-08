package promiseofblood.umpabackend.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Username;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

  boolean existsByUsername(Username username);

  Optional<User> findByOauth2User_ProviderNameAndOauth2User_ProviderUid(
      String providerName, String providerUid);
}
