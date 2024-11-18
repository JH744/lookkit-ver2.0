package synerjs.lookkit2nd.order;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID") 
    private User user;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "ORDER_COMMENT")
    private String orderComment;

    @Column(name = "ORDER_DATE")
    private Timestamp orderDate;

    @Column(name = "ORDER_ADDRESSEE")
    private String orderAddressee;

    @Column(name = "ORDER_ADDRESS")
    private String orderAddress;

    @Column(name = "ORDER_PHONE")
    private String orderPhone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();


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
