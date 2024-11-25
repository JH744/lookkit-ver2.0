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


    public List<ProductDTO> getProductsByCategory(String type, String sort) {

        //초기값설정 기본 정렬은 오래된순
        Sort sorting = Sort.by(Sort.Direction.ASC, "productId");

        if (sort != null) {
            switch (sort) {
                case "lowPrice":
                    sorting = Sort.by(Sort.Direction.ASC, "productPrice"); // 가격낮은순
                    break;
                case "highPrice":
                    sorting = Sort.by(Sort.Direction.DESC, "productPrice");// 가격 높은순
                    break;
                case "latest":
                    sorting = Sort.by(Sort.Direction.DESC, "productId");// 최신 순
                    break;
                case "oldest":
                    sorting = Sort.by(Sort.Direction.ASC, "productId"); // 오래된 순
                    break;
                default:
                    // 잘못된 정렬 옵션은 기본값 사용
                    sorting = Sort.by(Sort.Direction.ASC, "productId");
            }
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


