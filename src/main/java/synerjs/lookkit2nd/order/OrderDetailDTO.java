package synerjs.lookkit2nd.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderDetailDTO {

    private Long orderItemId;
    private Long orderId;
    private Long productId;     // 단일 상품 ID, null 가능
    private Long codiId;        // 코디 상품 ID, null 가능
    private Integer quantity;
    private Boolean isPurchaseConfirmed;
    private LocalDate rentalStartDate; // 대여 시작일, null 가능
    private LocalDate rentalEndDate;   // 반납일, null 가능

    public OrderDetailDTO(Long orderItemId, Long orderId, Long productId, Long codiId, Integer quantity, Boolean isPurchaseConfirmed, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.codiId = codiId;
        this.quantity = quantity;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }
}
