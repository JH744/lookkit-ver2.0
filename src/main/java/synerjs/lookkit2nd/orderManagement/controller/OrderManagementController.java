package synerjs.lookkit2nd.orderManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.order.OrderDTO;
import synerjs.lookkit2nd.order.OrderService;
import synerjs.lookkit2nd.orderManagement.dto.OrderManagementRequestDTO;
import synerjs.lookkit2nd.orderManagement.dto.OrderManagementResponseDTO;
import synerjs.lookkit2nd.orderManagement.service.OrderManagementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage/manage")
@RequiredArgsConstructor
public class OrderManagementController {
    private final OrderManagementService service;
    private final OrderService orderService;

    @GetMapping("/{userId}")
    public BaseResponse<?> getOrderManagement(@PathVariable Long userId){
        List<OrderDTO> list =  orderService.getOrderDetailsByUserId(userId);
        Map<String, Long> statusCounts = service.getOrderStatusCounts(userId);

        Map<String,?> response = Map.of(
                "products", list,
                "countPending", statusCounts.getOrDefault("pending", 0L),
                "countShipped", statusCounts.getOrDefault("shipped", 0L),
                "countDelivered", statusCounts.getOrDefault("delivered", 0L),
                "countCompleted", statusCounts.getOrDefault("completed", 0L)
        );
        return new BaseResponse<>(response);
    }

    @PatchMapping
    public BaseResponse<Void> updateConfirmStatus(@RequestBody OrderManagementRequestDTO request){
        service.updateConfirmStatus(request);
        return new BaseResponse<>();
    }
}
