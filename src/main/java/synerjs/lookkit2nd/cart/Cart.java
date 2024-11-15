package synerjs.lookkit2nd.cart;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

import java.time.LocalDate;

@Setter
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

    private Long productId; // 단일 상품 ID, null 가능
    private Long codiId;    // 코디 상품 ID, null 가능

    @Column(name = "rental_start_date", nullable = true)
    private LocalDate rentalStartDate; // 대여 시작일, null 가능

    @Column(name = "rental_end_date", nullable = true)
    private LocalDate rentalEndDate;   // 반납일, null 가능

    private Integer quantity;

    @Builder
    public Cart(User user, Long productId, Long codiId, LocalDate rentalStartDate, LocalDate rentalEndDate, Integer quantity) {
        this.user = user;
        this.productId = productId;
        this.codiId = codiId;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.quantity = quantity;
    }
}
