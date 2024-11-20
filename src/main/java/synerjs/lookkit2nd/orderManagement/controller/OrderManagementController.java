package synerjs.lookkit2nd.orderManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.orderManagement.dto.OrderManagementDTO;
import synerjs.lookkit2nd.orderManagement.service.OrderManagementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage/manage")
@RequiredArgsConstructor
public class OrderManagementController {
    private final OrderManagementService service;

    @GetMapping("/{userId}")
    public BaseResponse<?> getOrderManagement(@PathVariable Long userId){
        List<OrderManagementDTO> list =  service.getProductsByOrder(userId);
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

    @PatchMapping("{orderId}")
    public BaseResponse<Void> updateConfirmStatus(@PathVariable Long orderId){
        service.updateConfirmStatus(orderId);
        return new BaseResponse<>();
    }
}
