package synerjs.lookkit2nd.codi;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.inquiry.dto.CodiProductDTO;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

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


    //전체 코디셋
    public List<CodiDTO> getCodiSets() {
        return codiRepository.findAll().stream()
                .map(codi -> new CodiDTO(
                        codi.getCodiId(),
                        codi.getCodiName(),
                        codi.getCodiDescription(),
                        codi.getCodiThumbnail(),
                        codi.getCodiPrice(),
                        codi.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public List<CodiProductDTO> getAllCoordiWithProducts() {
        List<Object[]> results = codiRepository.findTop10CoordiWithProductsNative();

        return results.stream()
                .map(row -> {
                    Long codiId = ((Number) row[0]).longValue();
                    List<Product> products = productRepository.findByCodi_CodiId(codiId);
                    return new CodiProductDTO(
                            codiId,
                            (String) row[1],  // codiName
                            (String) row[2],  // codiThumbnail
                            (Integer) row[3], // codiPrice
                            products          // 연관된 상품 리스트
                    );
                })
                .toList();
    }

     // 특정 코디 ID에 속하는 상품 조회
    public List<Product> getProductsByCodiId(Long codiId) {
        return productRepository.findByCodi_CodiId(codiId);
    }
}



