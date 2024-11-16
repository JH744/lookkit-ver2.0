package synerjs.lookkit2nd.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {

    private Long userId;
    private Long wishlistId;
    private Long productId;
    private Long codiId;

    // DTO -> Entity
    @Builder
    public Wishlist toEntity(Product product, Codi codi) {
        return Wishlist.builder()
                .userId(userId)
                .product(product)
                .codi(codi)
                .build();
    }

}
