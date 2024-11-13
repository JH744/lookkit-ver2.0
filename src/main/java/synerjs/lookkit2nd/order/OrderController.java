package synerjs.lookkit2nd.order;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class OrderController {


//     @PostMapping("/order")
//     public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
//     Order order = Order.builder()
//             .userId(orderDTO.getUserId())
//             .totalAmount(orderDTO.getTotalAmount())
//             .orderStatus(orderDTO.getOrderStatus())
//             .orderComment(orderDTO.getOrderComment())
//             .orderDate(orderDTO.getOrderDate())
//             .orderAddressee(orderDTO.getOrderAddressee())
//             .orderAddress(orderDTO.getOrderAddress())
//             .orderPhone(orderDTO.getOrderPhone())
//             .build();

//     orderService.saveOrder(order);
//     return ResponseEntity.ok("Order created successfully");
// }
 
    @PostMapping("/buy-now/product")
public OrderDTO buyNowProduct(
        @RequestParam Long productId,
        @RequestParam String productName,
        @RequestParam Integer quantity,
        @RequestParam Integer price) {

    OrderDTO orderDTO = OrderDTO.builder()
            .itemId(productId)
            .itemName(productName)
            .quantity(quantity)
            .price(price * quantity)
            .build();

    return orderDTO;
}

@PostMapping("/rent-now/codi")
public OrderDTO rentNowCodi(
        @RequestParam Long codiId,
        @RequestParam String codiName,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate,
        @RequestParam Integer rentalPrice) {

    OrderDTO orderDTO = OrderDTO.builder()
            .itemId(codiId)
            .itemName(codiName)
            .startDate(startDate)
            .endDate(endDate)
            .price(rentalPrice)
            .build();

    return orderDTO;
}


}


