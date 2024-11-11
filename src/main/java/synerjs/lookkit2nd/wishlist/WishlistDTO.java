package synerjs.lookkit2nd.wishlist;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WishlistDTO {
    private long wishlistId;
    private long userId;
    private long productId;
    private long codiId;

    public static WishlistDTO fromEntity(Wishlist wishlist) {
        return WishlistDTO.builder()
                .wishlistId(wishlist.getWishlistId())
                .userId(wishlist.getUser().getUserId())
                .productId(wishlist.getProduct().getProductId())
                .codiId(wishlist.getCodi().getCodiId())
                .build();

    }
}
