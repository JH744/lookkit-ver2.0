package synerjs.lookkit2nd.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
public class OrderDetailDTO {

    private Long userId;
    private Long orderItemId;
    private Long orderId;
    private Long productId;     // 단일 상품 ID, null 가능
    private Long codiId;        // 코디 상품 ID, null 가능
    private String productName;
    private String brandName;
    private Integer quantity;
    private Boolean isPurchaseConfirmed;
    private LocalDate rentalStartDate; // 대여 시작일, null 가능
    private LocalDate rentalEndDate;   // 반납일, null 가능
    private Integer productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @Builder
    public OrderDetailDTO(Long userId, Long orderItemId, Long orderId, Long productId, String productName, String brandName, Long codiId, Integer quantity, Boolean isPurchaseConfirmed, LocalDate rentalStartDate, LocalDate rentalEndDate, Integer productPrice) {
        this.userId = userId;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.codiId = codiId;
        this.productName = productName;
        this.brandName = brandName;
        this.quantity = quantity;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.productPrice = productPrice;
    }
}
