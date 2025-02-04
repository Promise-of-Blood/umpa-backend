package promiseofblood.umpabackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import promiseofblood.umpabackend.user.entitiy.region.RegionalLocalGovernment;

public interface RegionRepository extends JpaRepository<RegionalLocalGovernment, Long> {
}
