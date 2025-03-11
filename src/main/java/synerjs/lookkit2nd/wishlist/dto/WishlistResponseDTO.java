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

    // 상품
    private Long productId;
    private String productName;
    private String brandName;
    private Integer productPrice;
    private String productThumbnail;

    //코디
    private Long codiId;
    private String codiName;
    private String codiDescription;
    private Integer codiPrice;
    private String codiThumbnail;

    private Long likeCount;


    // Entity에서 DTO로 변환하는 정적 메서드
    public static WishlistResponseDTO fromEntity(Wishlist wishlist, Long likeCount) {
        return WishlistResponseDTO.builder()
                .wishlistId(wishlist.getWishlistId())
                .productId(wishlist.getProduct() != null ? wishlist.getProduct().getProductId() : null)
                .productName(wishlist.getProduct() != null ? wishlist.getProduct().getProductName() : null)
                .brandName(wishlist.getProduct() != null ? wishlist.getProduct().getBrandName() : null)
                .productPrice(wishlist.getProduct() != null ? wishlist.getProduct().getProductPrice() : 0)
                .productThumbnail(wishlist.getProduct() != null ? wishlist.getProduct().getProductThumbnail() : null)
                .codiId(wishlist.getCodi() != null ? wishlist.getCodi().getCodiId() : null)
                .codiName(wishlist.getCodi() != null ? wishlist.getCodi().getCodiName() : null)
                .codiDescription(wishlist.getCodi() != null ? wishlist.getCodi().getCodiDescription() : null)
                .codiThumbnail(wishlist.getCodi() != null ? wishlist.getCodi().getCodiThumbnail() : null)
                .likeCount(likeCount)
                .build();
    }

}
