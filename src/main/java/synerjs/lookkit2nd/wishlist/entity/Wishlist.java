package synerjs.lookkit2nd.wishlist.entity;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODI_ID")
    private Codi codi;

}
