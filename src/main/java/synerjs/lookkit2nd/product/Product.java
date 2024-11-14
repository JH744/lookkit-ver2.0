package synerjs.lookkit2nd.product;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.codi.Codi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    private Codi codi;

    private String productName;

    private String productDescription;

    private BigDecimal productPrice;

    private String brandName;

    private Integer productStock;

    private String genderTarget;

    private String productThumbnail;

    private LocalDateTime productCreatedAt;

    private LocalDateTime productUpdatedAt;

}
