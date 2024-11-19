package synerjs.lookkit2nd.codi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface CodiRepository extends JpaRepository<Codi,Long> {





    // 코디 세트와 연관된 상품 리스트를 최대 10개 조회하는 메서드
    @Query(value = """
        SELECT c.codi_id AS codiId, 
               c.codi_name AS codiName,
               c.codi_thumbnail AS codiThumbnail, 
               c.codi_price AS codiPrice
        FROM codi c
        GROUP BY c.codi_id
        ORDER BY c.codi_id
        LIMIT 20
        """, nativeQuery = true)
    List<Object[]> findTop10CoordiWithProductsNative();



}
