package synerjs.lookkit2nd.codi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodiRepository extends JpaRepository<Codi,Long> {



    // 최신 코디 세트 8개 조회
    @Query("SELECT c FROM Codi c ORDER BY c.codiId")
    List<Codi> findTop8ByOrderByCodiIdAsc();


    /**
     * 코디 세트와 연관된 상품 데이터를 최대 10개 조회하는 메서드.
     * @return 코디 세트 리스트, 각 코디 세트는 연관된 상품 정보를 포함.
     */
    @Query("SELECT c FROM Codi c LEFT JOIN FETCH c.products p WHERE c.codiId IS NOT NULL")
    List<Codi> findTop10WithProducts();



}
