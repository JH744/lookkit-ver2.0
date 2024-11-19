package synerjs.lookkit2nd.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {




    // 카테고리로 조회
    @Query("SELECT p FROM Product p WHERE p.category.categoryType = :type")
    List<Product> findByCategoryType(@Param("type") String type);

     // 특정 코디 ID에 속하는 모든 상품을 조회하는 메서드.
    List<Product> findByCodi_CodiId(Long codiId);


}
