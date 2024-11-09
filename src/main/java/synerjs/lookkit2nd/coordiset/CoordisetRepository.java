package synerjs.lookkit2nd.coordiset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordisetRepository extends JpaRepository<Coordiset,Long> {
}
