package synerjs.lookkit2nd.cart;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CartDTO {

    private Long cartId;
    private Long userId;
    private Long productId;    // 단일 상품 ID
    private Long codiId;       // 코디 상품 ID
    private LocalDate rentalStartDate; // 대여 시작일, null 가능
    private LocalDate rentalEndDate;   // 반납일, null 가능
    private Integer quantity;          // 수량

    public CartDTO(Long cartId, Long userId, Long productId, Long codiId, LocalDate rentalStartDate, LocalDate rentalEndDate, Integer quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.codiId = codiId;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.quantity = quantity;
    }
}
