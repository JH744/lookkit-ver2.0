package synerjs.lookkit2nd.order;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/complete")
    public ResponseEntity<Long> completeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            // 주문을 저장하고 반환된 주문 ID를 받습니다.
            Long orderId = orderService.saveOrder(orderDTO);
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            // 주문을 저장하는 도중 오류가 발생하면 500 에러를 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null);
        }
    }
     
 
     // 특정 주문 완료 화면 정보를 위한 API 추가 (orderComplete에 사용될 데이터 조회)
     @GetMapping("/orderComplete")
     public ResponseEntity<OrderDTO> getOrderCompleteInfo(@RequestParam("orderId") Long orderId) {
         try {
             OrderDTO orderDTO = orderService.getOrderDetailsByOrderId(orderId);
             if (orderDTO != null) {
                 return ResponseEntity.ok(orderDTO);
             } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
             }
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(null);
         }
     }
     
 }



