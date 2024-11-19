package synerjs.lookkit2nd.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.codi.Codi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    @JsonBackReference // 순환 참조 방지
    private Codi codi;

    private String productName;
    private String brandName;
    private String productDescription;

    private Integer productPrice;


    private Integer productStock;
    private String genderTarget;
    private String productThumbnail;

    private Timestamp productCreatedAt;
    private Timestamp productUpdatedAt;

    @Builder
    public Product(Category category, String productName, String brandName, String productDescription, Integer productPrice,
                   Integer productStock, String genderTarget, String productThumbnail, Timestamp productCreatedAt, Timestamp productUpdatedAt) {
        this.category = category;
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
