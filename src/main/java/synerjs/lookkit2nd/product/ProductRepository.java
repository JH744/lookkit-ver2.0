package synerjs.lookkit2nd.product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    //카테고리 + sort 적용
    List<Product> findByCategory_CategoryType(String categoryType, Sort sort);

    //모든상품 + sort 적용
    List<Product> findAll(Sort sort);

    // 특정 코디 ID에 속하는 모든 상품을 조회하는 메서드.
    List<Product> findByCodi_CodiId(Long codiId);


    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.brandName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProductsByKeyword(@Param("keyword") String keyword);

}
