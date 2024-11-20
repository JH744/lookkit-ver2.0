package synerjs.lookkit2nd.orderManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class OrderManagementDTO {
    private Long userId;
    private Long orderId;
    private Timestamp orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private String brandName;
    private String productThumbnail;
    private boolean isPurchaseConfirmed;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public OrderManagementDTO(Long userId, Long orderId, Timestamp orderDate, BigDecimal totalAmount, String orderStatus,
                              Long productId, String productName, Integer productPrice, int quantity,
                              String brandName, String productThumbnail, boolean isPurchaseConfirmed,
                              LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.brandName = brandName;
        this.productThumbnail = productThumbnail;
        this.isPurchaseConfirmed = isPurchaseConfirmed;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }


}
