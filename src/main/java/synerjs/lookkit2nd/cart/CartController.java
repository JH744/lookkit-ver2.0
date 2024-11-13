package synerjs.lookkit2nd.cart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
     public List<CartDTO> getCartItems(@RequestParam(name = "userId") Long userId) {
         User user = userService.getUserById(userId);
         return cartService.getCartItemsByUser(user).stream()
                 .map(cart -> CartDTO.builder()
                         .cartId(cart.getCartId())
                         .userId(cart.getUser().getUserId())
                         .productId(cart.getProductId())
                         .codiId(cart.getCodiId())
                         .rentalStartDate(cart.getRentalStartDate())
                         .rentalEndDate(cart.getRentalEndDate())
                         .quantity(cart.getQuantity())
                         .build())
                 .collect(Collectors.toList());
     }
    
    @PostMapping("/add/product/{productId}")
    public String addProductToCart(
        @PathVariable("productId") Long productId, 
        @RequestParam(name = "quantity") int quantity, 
        @RequestParam(name = "userId") Long userId) {
        User user = userService.getUserById(userId);
        cartService.addProductToCart(user, productId, quantity);
        return "Product added to cart";
    }

    
    @PostMapping("/add/coordiset/{codiId}")
    public String addCoordisetToCart(
        @PathVariable("codiId") Long codiId,  // 명확한 이름 지정
        @RequestParam("rentalStartDate") String rentalStartDate,
        @RequestParam("rentalEndDate") String rentalEndDate,
        @RequestParam("userId") Long userId) {
    
        User user = userService.getUserById(userId);
        cartService.addCoordisetToCart(user, codiId, rentalStartDate, rentalEndDate);
        return "Coordiset added to cart";
    }

}
