package synerjs.lookkit2nd.order;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID") 
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_id", nullable = true)
    private Long productId; 

    @Column(name = "codi_id", nullable = true)
    private Long codiId; 

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_purchase_confirmed")
    private Boolean isPurchaseConfirmed;

    @Column(name = "rental_start_date", nullable = true)
    private LocalDate rentalStartDate; 
    @Column(name = "rental_end_date", nullable = true)
    private LocalDate rentalEndDate;   

    @Builder
    public OrderDetail(User user, Order order, Long productId, Long codiId, Integer quantity, Boolean isPurchaseConfirmed, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.user = user;
        this.order = order;
        this.productId = productId;
        this.codiId = codiId;
        this.quantity = quantity;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }
}
