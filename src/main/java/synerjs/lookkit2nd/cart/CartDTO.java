package synerjs.lookkit2nd.cart;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CartDTO {
    private Long cartId;
    private Long userId;
    private Long productId;
    private String productName;
    private String brandName;
    private Integer productPrice;
    private Long codiId;
    private String codiName;
    private Integer codiPrice;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private Integer quantity;

    public CartDTO(Long cartId, Long userId, Long productId, String productName, String brandName, Integer productPrice, 
                   Long codiId, String codiName, Integer codiPrice,
                   LocalDate rentalStartDate, LocalDate rentalEndDate, Integer quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.productPrice = productPrice;
        this.codiId = codiId;
        this.codiName = codiName;
        this.codiPrice = codiPrice;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.quantity = quantity;
    }
}
