package synerjs.lookkit2nd.orderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.order.Order;
import synerjs.lookkit2nd.orderManagement.dto.OrderManagementDTO;

import java.util.List;

@Repository
public interface OrderManagementRepository extends JpaRepository<Order, Long> {

    @Query("SELECT new synerjs.lookkit2nd.orderManagement.dto.OrderManagementDTO(" +
            "o.user.userId, o.orderId, o.orderDate, o.totalAmount, o.orderStatus, " +
            "od.productId, p.productName, p.productPrice, od.quantity, p.brandName, " +
            "p.productThumbnail, od.isPurchaseConfirmed, od.rentalStartDate, od.rentalEndDate) " +
            "FROM Order o " +
            "JOIN OrderDetail od ON o.orderId = od.order.orderId " +
            "JOIN Product p ON od.productId = p.productId " +
            "WHERE o.user.userId = :userId " +
            "ORDER BY o.orderId DESC")
    List<OrderManagementDTO> getProductsByOrder(long userId);

    @Modifying
    @Query("UPDATE OrderDetail od SET od.isPurchaseConfirmed = true WHERE od.order.orderId = :orderId")
    void updateConfirmStatus(Long orderId);
}
