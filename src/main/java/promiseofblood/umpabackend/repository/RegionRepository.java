package promiseofblood.umpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promiseofblood.umpabackend.domain.RegionalLocalGovernment;

@Repository
public interface RegionRepository extends JpaRepository<RegionalLocalGovernment, Long> {
}
