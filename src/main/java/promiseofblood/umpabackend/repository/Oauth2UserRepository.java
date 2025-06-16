package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;

@Repository
public interface Oauth2UserRepository extends JpaRepository<Oauth2User, Long> {

  boolean existsByProviderUidAndProviderName(String providerUid, String providerName);

}
