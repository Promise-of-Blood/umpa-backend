package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.entitiy.RegionCategory;

@Repository
public interface RegionRepository extends JpaRepository<RegionCategory, Long> {

}
