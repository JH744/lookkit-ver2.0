package synerjs.lookkit2nd.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import synerjs.lookkit2nd.user.User;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByUser(User user);

//    @Query("SELECT new synerjs.lookkit2nd.cart.CartDTO(" +
//       "c.cartId, c.user.userId, p.productId, p.productName, p.brandName, p.productPrice, co.codiId, co.codiName, co.codiPrice, c.rentalStartDate, c.rentalEndDate, c.quantity) " +
//       "FROM Cart c " +
//       "LEFT JOIN Product p ON c.productId = p.productId " +
//       "LEFT JOIN Codi co ON c.codiId = co.codiId " +
//       "WHERE c.user = :user")
//    List<CartDTO> findCartItemsByUser(@Param("user") User user);



}
