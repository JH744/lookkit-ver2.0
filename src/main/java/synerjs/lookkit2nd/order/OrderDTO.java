package synerjs.lookkit2nd.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String orderComment;
    private Timestamp orderDate;
    private String orderAddressee;
    private String orderAddress;
    private String orderPhone;
    
    private Long itemId;           
    private String itemName;       
    private Integer quantity;       
    private LocalDate startDate;    
    private LocalDate endDate;      
    private Integer price;  

    public OrderDTO(Long orderId, Long userId, BigDecimal totalAmount, String orderStatus, String orderComment, Timestamp orderDate, String orderAddressee, String orderAddress, String orderPhone, Long itemId, String itemName, Integer quantity, LocalDate startDate, LocalDate endDate, Integer price) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderComment = orderComment;
        this.orderDate = orderDate;
        this.orderAddressee = orderAddressee;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
