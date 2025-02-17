package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.SocialUser;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
