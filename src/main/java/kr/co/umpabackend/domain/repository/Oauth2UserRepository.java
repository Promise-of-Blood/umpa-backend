package kr.co.umpabackend.domain.repository;

import java.util.Optional;
import kr.co.umpabackend.domain.entity.Oauth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2UserRepository extends JpaRepository<Oauth2User, Long> {

  Optional<Oauth2User> findByProviderNameAndProviderUid(String providerName, String providerUid);

  boolean existsByProviderUidAndProviderName(String providerUid, String providerName);
}
