package synerjs.lookkit2nd.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }


    // 카테고리별 상품목록
    public List<ProductDTO> getProductsByCategory(String type, String sort) {

        //초기값설정 기본 정렬은 오래된순
        Sort sorting = Sort.by(Sort.Direction.ASC, "productId");

        if (sort != null) {
            sorting =
                switch (sort) {
                    case "lowPrice" -> {
                        yield Sort.by(Sort.Direction.ASC, "productPrice"); // 가격낮은순
                    }
                    case "highPrice" -> {
                        yield Sort.by(Sort.Direction.DESC, "productPrice");// 가격 높은순
                    }
                    case "latest" -> {
                        yield Sort.by(Sort.Direction.DESC, "productId");// 최신 순
                    }
                    case "oldest" -> {
                        yield Sort.by(Sort.Direction.ASC, "productId"); // 오래된 순
                    }
                    default -> {
                        yield Sort.by(Sort.Direction.ASC, "productId"); //          // 잘못된 정렬 옵션은 기본값 사용
                    }
                };
        }

        // 정렬된 결과를 가져오기
        List<Product> products
            = type.equals("all")
            ? productRepository.findAll(sorting)
            : productRepository.findByCategory_CategoryType(type, sorting);

        return products.stream()
            .map(product -> ProductDTO.builder()
                .productId(product.getProductId())
                .categoryId(product.getCategory().getCategoryId())
                .productName(product.getProductName())
                .brandName(product.getBrandName())
                .productPrice(product.getProductPrice())
                .genderTarget(product.getGenderTarget())
                .productThumbnail(product.getProductThumbnail())
                .build()
            )
            .collect(Collectors.toList());
    }



    // 상품 검색
    public List<ProductDTO> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.searchProductsByKeyword(keyword);
        return products.stream()
            .map(product -> ProductDTO.builder()
                .productId(product.getProductId())
                .categoryId(product.getCategory().getCategoryId())
                .productName(product.getProductName())
                .brandName(product.getBrandName())
                .productPrice(product.getProductPrice())
                .genderTarget(product.getGenderTarget())
                .productThumbnail(product.getProductThumbnail())
                .build())
            .collect(Collectors.toList());
    }

}


