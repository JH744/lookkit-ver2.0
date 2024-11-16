package synerjs.lookkit2nd.cart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

     // 장바구니에 있는 모든 상품 조회
    @GetMapping("/items")
    public List<CartDTO> getCartItems(@RequestParam("userId") Long userId) {
        User user = userService.getUserById(userId);
        return cartService.getCartItemsByUser(user).stream()
                .map(cart -> CartDTO.builder()
                        .cartId(cart.getCartId())
                        .userId(cart.getUserId())
                        .productId(cart.getProductId())
                        .codiId(cart.getCodiId())
                        .rentalStartDate(cart.getRentalStartDate())
                        .rentalEndDate(cart.getRentalEndDate())
                        .quantity(cart.getQuantity())
                        .productName(cart.getProductName())
                        .codiName(cart.getCodiName())
                        .brandName(cart.getBrandName())
                        .productPrice(cart.getProductPrice())
                        .codiPrice(cart.getCodiPrice())
                        .build())
                .collect(Collectors.toList());
    }
    
    @PostMapping("/add/product/{productId}")
    public ResponseEntity<String> addProductToCart(
        @PathVariable("productId") Long productId, 
        @RequestParam("quantity") int quantity, 
        @RequestParam("userId") Long userId) {

        try {
            User user = userService.getUserById(userId);
            cartService.addProductToCart(user, productId, quantity);
            return ResponseEntity.ok("Product added to cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to add product to cart: " + e.getMessage());
        }                         
    }

    
    @PostMapping("/add/codi/{codiId}")
    public ResponseEntity<String> addCodiToCart(
        @PathVariable("codiId") Long codiId, 
        @RequestParam("rentalStartDate") String rentalStartDate,
        @RequestParam("rentalEndDate") String rentalEndDate,
        @RequestParam("userId") Long userId) {
    
        try {
            User user = userService.getUserById(userId);
            cartService.addCodiToCart(user, codiId, rentalStartDate, rentalEndDate);
            return ResponseEntity.ok("Codi added to cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to add codi to cart: " + e.getMessage());
        }                         
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCartItem(
    @RequestParam("userId") Long userId,
    @RequestBody Map<String, Object> updateRequest) {
        try {
        User user = userService.getUserById(userId);
        cartService.updateCartItem(updateRequest);
        return ResponseEntity.ok("Cart item updated successfully");
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to update cart item: " + e.getMessage());
    }
    }


    @PostMapping("/delete")
    public ResponseEntity<String> deleteCartItem(@RequestBody Map<String, Long> request) {
    try {
        Long cartId = request.get("cartId");
        cartService.deleteCartItemById(cartId);
        return ResponseEntity.ok("Cart item deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to delete cart item: " + e.getMessage());
    }
}

    @PostMapping("/delete/bulk")
    public ResponseEntity<String> deleteMultipleCartItems(@RequestBody Map<String, List<Long>> request) {
    try {
        List<Long> cartIds = request.get("cartIds");
        cartService.deleteMultipleCartItemsByIds(cartIds);
        return ResponseEntity.ok("Selected cart items deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to delete selected cart items: " + e.getMessage());
    }
}


}
