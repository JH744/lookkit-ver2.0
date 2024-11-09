package synerjs.lookkit2nd.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String productName;

    private String productDescription;

    private BigDecimal productPrice;

    private Integer productStock;

    private String genderTarget;

    private String productThumbnail;

    private Timestamp productCreatedAt;

    private Timestamp productUpdatedAt;

    @Builder
    public Product(Category category, String productName, String productDescription, BigDecimal productPrice,
                   Integer productStock, String genderTarget, String productThumbnail, Timestamp productCreatedAt, Timestamp productUpdatedAt) {
        this.category = category;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.genderTarget = genderTarget;
        this.productThumbnail = productThumbnail;
        this.productCreatedAt = productCreatedAt;
        this.productUpdatedAt = productUpdatedAt;
    }
}
