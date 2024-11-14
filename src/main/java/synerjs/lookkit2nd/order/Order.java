package synerjs.lookkit2nd.order;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private BigDecimal totalAmount;

    private String orderStatus;

    private String orderComment;

    private Timestamp orderDate;

    private String orderAddressee;

    private String orderAddress;

    private String orderPhone;

    @Builder
    public Order(User user, BigDecimal totalAmount, String orderStatus, String orderComment,
                 Timestamp orderDate, String orderAddressee, String orderAddress, String orderPhone) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderComment = orderComment;
        this.orderDate = orderDate;
        this.orderAddressee = orderAddressee;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
    }
}