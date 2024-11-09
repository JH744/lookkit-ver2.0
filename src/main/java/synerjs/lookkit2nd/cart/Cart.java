package synerjs.lookkit2nd.cart;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long productId;
    private Long codiId;

    @Builder
    public Cart(User user, Long productId, Long codiId) {
        this.user = user;
        this.productId = productId;
        this.codiId = codiId;
    }
}
