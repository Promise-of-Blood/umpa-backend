package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.user.entitiy.region.RegionalLocalGovernment;

@Repository
public interface RegionRepository extends JpaRepository<RegionalLocalGovernment, Long> {
}
