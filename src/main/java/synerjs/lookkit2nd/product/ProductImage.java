package synerjs.lookkit2nd.product;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products_images")
@ToString
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String imagePath;

    @Builder
    public ProductImage(Product product, String imagePath) {
        this.product = product;
        this.imagePath = imagePath;
    }
}
