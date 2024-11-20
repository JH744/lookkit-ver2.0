package synerjs.lookkit2nd.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class WishlistResponseDTO {

    private Long wishlistId;
    private Long productId;
    private String productName;
    private String brandName;
    private int productPrice;
    private String productThumbnail;
    private Long likeCount;

    // Entity에서 DTO로 변환하는 정적 메서드
    public static WishlistResponseDTO fromEntity(Wishlist wishlist, Long likeCount) {
        return new WishlistResponseDTO(
                wishlist.getWishlistId(),
                wishlist.getProduct().getProductId(),
                wishlist.getProduct().getProductName(),
                wishlist.getProduct().getBrandName(),
                wishlist.getProduct().getProductPrice(),
                wishlist.getProduct().getProductThumbnail(),
                likeCount
        );
    }

}
