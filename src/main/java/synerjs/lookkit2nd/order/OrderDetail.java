package synerjs.lookkit2nd.order;

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

    @Builder
    public OrderDetail(Order order, Long productId, Long codiId, Integer quantity, Boolean isPurchaseConfirmed) {
        this.order = order;
        this.productId = productId;
        this.codiId = codiId;
        this.quantity = quantity;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
    }
}
