package synerjs.lookkit2nd.common.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOrderDTO {
    private Long orderId;
    private String userUuid;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String orderComment;
    private Timestamp orderDate;
    private String orderAddress;
    private String orderAddressee;
    private String orderPhone;
}