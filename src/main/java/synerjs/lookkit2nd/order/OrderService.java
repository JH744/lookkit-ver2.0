package synerjs.lookkit2nd.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
    }

    @Transactional
    public Long saveOrder(OrderDTO orderDTO) {
    User user = userService.getUserById(orderDTO.getUserId());

    // Order 엔티티 생성
    Order order = Order.builder()
            .user(user)
            .totalAmount(orderDTO.getTotalAmount())
            .orderStatus("pending") // 초기 상태 설정
            .orderComment(orderDTO.getOrderComment())
            .orderDate(new Timestamp(System.currentTimeMillis()))
            .orderAddressee(orderDTO.getOrderAddressee())
            .orderAddress(orderDTO.getOrderAddress())
            .orderPhone(orderDTO.getOrderPhone())
            .build();

    // Order 엔티티를 데이터베이스에 저장
    orderRepository.save(order);

    // OrderDetail 엔티티 생성 및 저장
    for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
        OrderDetail.OrderDetailBuilder detailBuilder = OrderDetail.builder()
                .order(order)
                .user(user)
                .isPurchaseConfirmed(false)
                .quantity(detailDTO.getQuantity() != null ? detailDTO.getQuantity() : 1);

        if (detailDTO.getProductId() != null) {
            // 단일 상품인 경우
            detailBuilder.productId(detailDTO.getProductId());
        } else if (detailDTO.getCodiId() != null) {
            // 코디 상품인 경우, 대여 기간 정보도 함께 설정
            detailBuilder.codiId(detailDTO.getCodiId())
                         .rentalStartDate(detailDTO.getRentalStartDate())
                         .rentalEndDate(detailDTO.getRentalEndDate());
        } else {
            // 상품 정보가 없다면 로그를 남김
            System.out.println("Warning: Both productId and codiId are null for an OrderDetail");
        }

        // OrderDetail 엔티티를 데이터베이스에 저장
        OrderDetail orderDetail = detailBuilder.build();
        orderDetailRepository.save(orderDetail);

        order.getOrderDetails().add(orderDetail);
    }

    // 저장된 order의 ID 반환
    return order.getOrderId();
}




    // 주문 상세 정보 조회
    public OrderDTO getOrderDetailsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        // Order와 OrderDetail을 OrderDTO로 변환
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(detail -> OrderDetailDTO.builder()
                        .orderItemId(detail.getOrderItemId())
                        .orderId(detail.getOrder().getOrderId())
                        .productId(detail.getProductId())
                        .codiId(detail.getCodiId())
                        .quantity(detail.getQuantity())
                        .isPurchaseConfirmed(detail.getIsPurchaseConfirmed())
                        .rentalStartDate(detail.getRentalStartDate())
                        .rentalEndDate(detail.getRentalEndDate())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .orderComment(order.getOrderComment())
                .orderDate(order.getOrderDate())
                .orderAddressee(order.getOrderAddressee())
                .orderAddress(order.getOrderAddress())
                .orderPhone(order.getOrderPhone())
                .orderDetails(orderDetailDTOs)
                .build();
    }
}
