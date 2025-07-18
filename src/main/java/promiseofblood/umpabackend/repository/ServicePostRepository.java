package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entity.ServicePost;

@Repository
public interface ServicePostRepository extends JpaRepository<ServicePost, Long> {

}
