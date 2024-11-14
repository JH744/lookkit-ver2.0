package synerjs.lookkit2nd.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistRequestDTO {

    private Long userId;

    // DTO -> Entity
    public Wishlist toEntity(Long userId, User user, Product product, Codi codi) {
        return Wishlist.builder()
                .wishlistId(userId)
                .user(user)
                .product(product)
                .codi(codi)
                .build();
    }

}
