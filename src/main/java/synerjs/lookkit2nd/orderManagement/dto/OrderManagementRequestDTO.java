package synerjs.lookkit2nd.orderManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderManagementRequestDTO {
    private Long orderId;
    private Long productId;
}
