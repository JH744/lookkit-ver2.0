package synerjs.lookkit2nd.product;

import org.springframework.web.bind.annotation.*;

import synerjs.lookkit2nd.order.OrderDTO;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/buy")
    public OrderDTO buyProduct(
        @RequestParam(name = "productId") Long productId,
        @RequestParam(name = "quantity") Integer quantity) {
    
    Product product = productService.getProductById(productId);
    Integer productPrice = product.getProductPrice();
    Integer totalPrice = productPrice * quantity;  // 총 금액 계산

    return OrderDTO.builder()
            .itemId(product.getProductId())
            .itemName(product.getProductName())
            .brandName(product.getBrandName())
            .quantity(quantity)
            .price(productPrice)
            .totalPrice(totalPrice)
            .build();
}
}
