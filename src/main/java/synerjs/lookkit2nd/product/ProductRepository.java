package synerjs.lookkit2nd.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {






    /**
     * 특정 코디 ID에 속하는 모든 상품을 조회하는 메서드.
     * @param codiId 코디 ID.
     * @return 해당 코디 ID에 속하는 상품 리스트.
     */
    List<Product> findByCodi_CodiId(Long codiId);
}
