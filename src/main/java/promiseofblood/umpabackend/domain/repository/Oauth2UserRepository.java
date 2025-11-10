package promiseofblood.umpabackend.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entity.Oauth2User;

@Repository
public interface Oauth2UserRepository extends JpaRepository<Oauth2User, Long> {

  Optional<Oauth2User> findByProviderNameAndProviderUid(String providerName, String providerUid);

  boolean existsByProviderUidAndProviderName(String providerUid, String providerName);
}
