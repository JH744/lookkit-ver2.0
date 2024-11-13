package synerjs.lookkit2nd.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synerjs.lookkit2nd.user.User;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByUser(User user);
}
