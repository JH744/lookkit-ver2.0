package synerjs.lookkit2nd.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class ProductDTO {

    private Long productId;
    private Long categoryId;
    private String productName;
    private String brandName;
    private String productDescription;
    private Integer productPrice;
    private Integer productStock;
    private String genderTarget;
    private String productThumbnail;
    private Timestamp productCreatedAt;
    private Timestamp productUpdatedAt;

    public ProductDTO(Long productId, Long categoryId, String productName, String brandName, String productDescription, Integer productPrice, Integer productStock, String genderTarget, String productThumbnail, Timestamp productCreatedAt, Timestamp productUpdatedAt) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.brandName = brandName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.genderTarget = genderTarget;
        this.productThumbnail = productThumbnail;
        this.productCreatedAt = productCreatedAt;
        this.productUpdatedAt = productUpdatedAt;
    }
}
