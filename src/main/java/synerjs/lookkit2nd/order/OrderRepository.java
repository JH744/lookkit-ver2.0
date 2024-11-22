package synerjs.lookkit2nd.order;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.common.dto.UserOrderDTO;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_UserId(Long userId);


    // 주문현황 조회 (Uid -> Uuid)
    @Query("SELECT new synerjs.lookkit2nd.common.dto.UserOrderDTO(" +
            "o.orderId, u.userUuid, o.totalAmount, o.orderStatus, " +
            "o.orderComment, o.orderDate, o.orderAddress, " +
            "o.orderAddressee, o.orderPhone) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "ORDER BY o.orderDate DESC")
    List<UserOrderDTO> getAllOrdersWithUserUuid();


    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
    int updateOrderStatus(Long orderId, String orderStatus);

}
