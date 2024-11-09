package synerjs.lookkit2nd.wishlist;

import jakarta.persistence.*;
import lombok.*;
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

    private Long productId;

    private Long codiId;

    @Builder
    public Wishlist(User user, Long productId, Long codiId) {
        this.user = user;
        this.productId = productId;
        this.codiId = codiId;
    }
}
