package synerjs.lookkit2nd.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.codi.Codi;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductProductId(Long productId);
    List<Review> findByCodiCodiId(Long codiId);
    List<Review> findByUserUserId(Long userId);
}
