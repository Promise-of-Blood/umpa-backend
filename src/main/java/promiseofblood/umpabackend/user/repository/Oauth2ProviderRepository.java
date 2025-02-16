package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import promiseofblood.umpabackend.user.entitiy.Oauth2Provider;

public interface Oauth2ProviderRepository extends JpaRepository<Oauth2Provider, Long> {
  Oauth2Provider getByName(String name);
}
