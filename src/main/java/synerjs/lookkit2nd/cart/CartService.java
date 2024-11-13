package synerjs.lookkit2nd.cart;

import java.util.List;

import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.user.User;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartItemsByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public void addProductToCart(User user, Long productId, int quantity) {
        // 제품을 장바구니에 추가하는 로직을 구현합니다.
        System.out.println("Adding product to cart for user: " + user.getUserName() +
                ", Product ID: " + productId + ", Quantity: " + quantity);
    }

    public void addCoordisetToCart(User user, Long codiId, String rentalStartDate, String rentalEndDate) {
        // 코디세트를 장바구니에 추가하는 로직을 구현합니다.
        System.out.println("Adding coordiset to cart for user: " + user.getUserName() +
                ", Codi ID: " + codiId + ", Start Date: " + rentalStartDate + ", End Date: " + rentalEndDate);
    }
}
