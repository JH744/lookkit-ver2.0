package synerjs.lookkit2nd.order;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId; // Assuming no direct relationship

    private Long codiId; // Assuming no direct relationship

    private Integer quantity;

    private Boolean isPurchaseConfirmed;

    @Column(name = "rental_start_date", nullable = true)
    private LocalDate rentalStartDate; // 대여 시작일, null 가능

    @Column(name = "rental_end_date", nullable = true)
    private LocalDate rentalEndDate;   // 반납일, null 가능

    @Builder
    public OrderDetail(Order order, Long productId, Long codiId, Integer quantity, Boolean isPurchaseConfirmed, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.order = order;
        this.productId = productId;
        this.codiId = codiId;
        this.quantity = quantity;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }
}
