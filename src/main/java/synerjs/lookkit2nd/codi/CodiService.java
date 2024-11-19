package synerjs.lookkit2nd.codi;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodiService {
    private final CodiRepository codiRepository;
    private final ProductRepository productRepository;



    public List<Codi> getAllCodis() {
        return codiRepository.findAll();
    }

    public Codi getCodiById(Long codiId) {
        return codiRepository.findById(codiId).orElse(null);
    }



     // 최신 코디 세트 8개 조회
    public List<Codi> getLatestEightCodiSets() {
        return codiRepository.findTop8ByOrderByCodiIdAsc();
    }

     // 코디 세트& 연관 상품 최대 10개 조회
    public List<Codi> getAllCoordiWithProducts() {
        return codiRepository.findTop10WithProducts();
    }

     // 특정 코디 ID에 속하는 상품 조회
    public List<Product> getProductsByCodiId(Long codiId) {
        return productRepository.findByCodi_CodiId(codiId);
    }
}



