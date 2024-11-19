package synerjs.lookkit2nd.cart;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiService;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductService;
import synerjs.lookkit2nd.user.User;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CodiService codiService;

    public CartService(CartRepository cartRepository, ProductService productService, CodiService codiService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.codiService = codiService;
    }

    public List<CartDTO> getCartItemsByUser(User user) {
    List<Cart> cartItems = cartRepository.findByUser(user);
    return cartItems.stream().map(cart -> {
        CartDTO.CartDTOBuilder dtoBuilder = CartDTO.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .productId(cart.getProductId())
                .codiId(cart.getCodiId())
                .rentalStartDate(cart.getRentalStartDate())
                .rentalEndDate(cart.getRentalEndDate())
                .quantity(cart.getQuantity());

        // Product 정보 추가
        if (cart.getProductId() != null) {
            Product product = productService.getProductById(cart.getProductId());
            dtoBuilder.productName(product.getProductName())
                      .brandName(product.getBrandName())
                      .productPrice(product.getProductPrice());
        }

        // Codi 정보 추가
        if (cart.getCodiId() != null) {
            Codi codi = codiService.getCodiById(cart.getCodiId());
            dtoBuilder.codiDescription(codi.getCodiDescription())
                      .codiPrice(codi.getCodiPrice());
        }

        return dtoBuilder.build();
    }).collect(Collectors.toList());
}


    public void addProductToCart(User user, Long productId, int quantity) {
        // 제품을 장바구니에 추가하는 로직을 구현합니다.
        System.out.println("Adding product to cart for user: " + user.getUserName() +
                ", Product ID: " + productId + ", Quantity: " + quantity);

        // Cart 객체 생성
        Cart newCart = Cart.builder()
                .user(user)
                .productId(productId)
                .quantity(quantity)
                .build();

        // Cart 객체를 데이터베이스에 저장
        cartRepository.save(newCart);
    }

    public void addCodiToCart(User user, Long codiId, String rentalStartDate, String rentalEndDate) {
        // 코디세트를 장바구니에 추가하는 로직을 구현합니다.
        System.out.println("Adding coordiset to cart for user: " + user.getUserName() +
                ", Codi ID: " + codiId + ", Start Date: " + rentalStartDate + ", End Date: " + rentalEndDate);

        // LocalDate로 변환
        LocalDate start = LocalDate.parse(rentalStartDate);
        LocalDate end = LocalDate.parse(rentalEndDate);

        // Cart 객체 생성
        Cart newCart = Cart.builder()
                .user(user)
                .codiId(codiId)
                .rentalStartDate(start)
                .rentalEndDate(end)
                .quantity(1)
                .build();

        // Cart 객체를 데이터베이스에 저장
        cartRepository.save(newCart);
    }

    @Transactional
    public void updateCartItem(Map<String, Object> updateRequest) {
    Long cartId = Long.valueOf(updateRequest.get("cartId").toString());
    Optional<Cart> cartOptional = cartRepository.findById(cartId);

    if (cartOptional.isPresent()) {
        Cart cart = cartOptional.get();

        // 수량 업데이트
        if (updateRequest.containsKey("quantity")) {
            Integer quantity = Integer.valueOf(updateRequest.get("quantity").toString());
            cart.setQuantity(quantity);
        }

        // 대여일/반납일 업데이트
        if (updateRequest.containsKey("rentalStartDate")) {
            String startDateString = (String) updateRequest.get("rentalStartDate");
            LocalDate startDate = LocalDate.parse(startDateString);
            cart.setRentalStartDate(startDate);
        }
        if (updateRequest.containsKey("rentalEndDate")) {
            String endDateString = (String) updateRequest.get("rentalEndDate");
            LocalDate endDate = LocalDate.parse(endDateString);
            cart.setRentalEndDate(endDate);
        }

        // 변경사항 저장
        cartRepository.save(cart);
    } else {
        throw new RuntimeException("Cart item not found");
    }
}


    @Transactional
    public void deleteCartItemById(Long cartId) {
    cartRepository.deleteById(cartId);
}

    @Transactional
    public void deleteMultipleCartItemsByIds(List<Long> cartIds) {
    cartRepository.deleteAllById(cartIds);
}


}
