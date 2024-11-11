package synerjs.lookkit2nd.wishlist;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.coordiset.Coordiset;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    private Coordiset codi;

}
