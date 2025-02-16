package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.user.entitiy.SocialUser;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
