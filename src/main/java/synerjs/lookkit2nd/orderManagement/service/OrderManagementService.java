package synerjs.lookkit2nd.orderManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.common.exception.BaseException;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;
import synerjs.lookkit2nd.orderManagement.dto.OrderManagementDTO;
import synerjs.lookkit2nd.orderManagement.repository.OrderManagementRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final OrderManagementRepository repository;

    public List<OrderManagementDTO> getProductsByOrder(long userId){
        return repository.getProductsByOrder(userId);
    }

    public Map<String, Long> getOrderStatusCounts(Long userId) {
        List<OrderManagementDTO> list = getProductsByOrder(userId);

        return list.stream()
                .collect(Collectors.groupingBy(OrderManagementDTO::getOrderId))
                .values().stream()
                .map(orderProducts -> orderProducts.get(0).getOrderStatus())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Transactional
    public void updateConfirmStatus(Long orderId) {
        repository.findById(orderId)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.ORDER_NOT_FOUND));
        repository.updateConfirmStatus(orderId);
    }
}
