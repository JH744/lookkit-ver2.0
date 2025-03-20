package synerjs.lookkit2nd.codi;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface CodiRepository extends JpaRepository<Codi, Long> {

    // 코디 세트와 연관된 상품 리스트 40개 조회 -제거예정
    @Query(value = """
        SELECT c.codi_id AS codiId, 
               c.codi_name AS codiName,
               c.codi_thumbnail AS codiThumbnail, 
               c.codi_price AS codiPrice
        FROM codi c
        ORDER BY c.codi_id DESC
        LIMIT 40
        """, nativeQuery = true)
    List<Object[]> findLast40CoordiWithProductsNative();


    // fetch join방식 테스트
//   @Query("""
//        SELECT DISTINCT c
//          FROM Codi c
//          LEFT JOIN FETCH c.products p
//          LEFT JOIN FETCH p.category
//         ORDER BY c.codiId DESC
//    """)
//    List<Codi> findAllCodisWithProducts();

//    EntityGraph 방식 테스트
//    @EntityGraph(attributePaths = {"products", "products.category"})
//    @Query("SELECT c FROM Codi c ORDER BY c.codiId DESC")
//    List<Codi> findAllCodisWithProducts();

    @EntityGraph(attributePaths = {"products", "products.category"})
    List<Codi> findAllByOrderByCodiIdDesc();

}
