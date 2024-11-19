package synerjs.lookkit2nd.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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


    public List<ProductDTO> getProductsByCategory(String type) {
        return productRepository.findByCategoryType(type).stream()
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

}


