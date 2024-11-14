package synerjs.lookkit2nd.codi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodiRepository extends JpaRepository<Codi,Long> {
}
