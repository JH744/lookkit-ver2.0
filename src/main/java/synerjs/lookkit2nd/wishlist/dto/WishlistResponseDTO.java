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

    private long productId;
    private String productName;
    private String brandName;
    private BigDecimal productPrice;
    private String productThumbnail;
    private int likeCount;

    public static WishlistResponseDTO of(Wishlist wishlist, int likeCount) {
        return WishlistResponseDTO.builder()
                .productId(wishlist.getProduct().getProductId())
                .brandName(wishlist.getProduct().getBrandName())
                .productPrice(wishlist.getProduct().getProductPrice())
                .productThumbnail(wishlist.getProduct().getProductThumbnail())
                .likeCount(likeCount)
                .build();
    }

}
