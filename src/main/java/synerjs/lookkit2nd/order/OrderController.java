package synerjs.lookkit2nd.order;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // // 주문을 DB에 저장하는 API
    // @PostMapping("/complete")
    // public ResponseEntity<String> completeOrder(@RequestBody OrderDTO orderDTO) {
    //     try {
    //         orderService.saveOrder(orderDTO);
    //         return ResponseEntity.ok("Order completed and saved successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                              .body("Failed to complete order: " + e.getMessage());
    //     }
    // }
}



