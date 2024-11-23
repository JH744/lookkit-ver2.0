package synerjs.lookkit2nd.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.common.dto.UserOrderDTO;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserService;
import synerjs.lookkit2nd.codi.CodiRepository;
import synerjs.lookkit2nd.product.ProductRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CodiRepository codiRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserService userService, ProductRepository productRepository, CodiRepository codiRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.codiRepository = codiRepository;
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

        // OrderDetail 엔티티 생성 및 Order와 연관 설정
        for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .user(user)
                    .order(order)
                    .productId(detailDTO.getProductId())
                    .codiId(detailDTO.getCodiId())
                    .quantity(detailDTO.getQuantity() != null ? detailDTO.getQuantity() : 1)
                    .rentalStartDate(detailDTO.getRentalStartDate())
                    .rentalEndDate(detailDTO.getRentalEndDate())
                    .productPrice(detailDTO.getProductPrice())
                    .isPurchaseConfirmed(false)
                    .build();

            order.addOrderDetail(orderDetail); // Order와 OrderDetail 관계 설정
        }

        // Order 엔티티를 데이터베이스에 저장 (연관된 OrderDetail도 자동으로 저장됨)
        orderRepository.save(order);

        return order.getOrderId();
    }

    // 주문 상세 정보 조회
    public OrderDTO getOrderDetailsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(detail -> {
                    String productName = null;
                    String brandName = null;

                    if (detail.getProductId() != null) {
                        Product product = productRepository.findById(detail.getProductId()).orElse(null);
                        if (product != null) {
                            productName = product.getProductName();
                            brandName = product.getBrandName();
                        }
                    } else if (detail.getCodiId() != null) {
                        Codi codi = codiRepository.findById(detail.getCodiId()).orElse(null);
                        if (codi != null) {
                            productName = codi.getCodiDescription();
                        }
                    }

                    return OrderDetailDTO.builder()
                            .orderItemId(detail.getOrderItemId())
                            .orderId(detail.getOrder().getOrderId())
                            .productId(detail.getProductId())
                            .codiId(detail.getCodiId())
                            .productName(productName)
                            .brandName(brandName)
                            .quantity(detail.getQuantity())
                            .isPurchaseConfirmed(detail.getIsPurchaseConfirmed())
                            .rentalStartDate(detail.getRentalStartDate())
                            .rentalEndDate(detail.getRentalEndDate())
                            .productPrice(detail.getProductPrice())
                            .build();
                })
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

    public List<OrderDTO> getOrderDetailsByUserId(Long userId) {
        // userId로 모든 주문(Order)을 조회합니다.
        List<Order> orders = orderRepository.findByUser_UserId(userId);

        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        // 각 주문을 OrderDTO로 변환하여 반환할 리스트를 생성합니다.
        return orders.stream()
                .map(order -> {
                    // 각 주문에 대한 OrderDetail을 조회합니다.
                    List<OrderDetail> orderDetails = orderDetailRepository.findByUser_UserId(userId);

                    // OrderDetail을 OrderDetailDTO로 변환합니다.
                    List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                            .map(detail -> {
                                String productName = null;
                                String brandName = null;

                                if (detail.getProductId() != null) {
                                    Product product = productRepository.findById(detail.getProductId()).orElse(null);
                                    if (product != null) {
                                        productName = product.getProductName();
                                        brandName = product.getBrandName();
                                    }
                                } else if (detail.getCodiId() != null) {
                                    Codi codi = codiRepository.findById(detail.getCodiId()).orElse(null);
                                    if (codi != null) {
                                        productName = codi.getCodiDescription();
                                    }
                                }

                                return OrderDetailDTO.builder()
                                        .orderItemId(detail.getOrderItemId())
                                        .orderId(detail.getOrder().getOrderId())
                                        .productId(detail.getProductId())
                                        .codiId(detail.getCodiId())
                                        .productName(productName)
                                        .brandName(brandName)
                                        .quantity(detail.getQuantity())
                                        .isPurchaseConfirmed(detail.getIsPurchaseConfirmed())
                                        .rentalStartDate(detail.getRentalStartDate())
                                        .rentalEndDate(detail.getRentalEndDate())
                                        .productPrice(detail.getProductPrice())
                                        .build();
                            })
                            .collect(Collectors.toList());

                    // Order를 OrderDTO로 변환합니다.
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
                })
                .collect(Collectors.toList());
    }


    // [관리자 주문현황] 모든주문리스트가져오기 (Uid > UserUuid 변경해 가져오기)
    public List<UserOrderDTO> getAllOrders() {
        return orderRepository.getAllOrdersWithUserUuid();
    }

    // [관리자 주문현황] 주문상태 업데이트
    public int updateOrderStatus(Long orderId, String orderStatus) {
        System.out.println("orderStatus>>>>>>>>>>>>" + orderStatus);
        return orderRepository.updateOrderStatus(orderId, orderStatus);
    }


}
