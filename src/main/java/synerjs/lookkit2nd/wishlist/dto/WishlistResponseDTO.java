package synerjs.lookkit2nd.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;

import java.math.BigDecimal;

@Getter
@Builder
public class WishlistResponseDTO {

    private Long productId;
    private String productName;
    private String brandName;
    private int productPrice;
    private String productThumbnail;
    private Long likeCount;

    // Hibernate가 사용할 수 있도록 필요한 모든 필드를 포함하는 생성자 추가
    public WishlistResponseDTO(Long productId, String productName, String brandName, int productPrice, String productThumbnail, Long likeCount) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.productPrice = productPrice;
        this.productThumbnail = productThumbnail;
        this.likeCount = likeCount;
    }

    // Entity에서 DTO로 변환하는 정적 메서드
    public static WishlistResponseDTO fromEntity(Wishlist wishlist, Long likeCount) {
        return new WishlistResponseDTO(
                wishlist.getProduct().getProductId(),
                wishlist.getProduct().getProductName(),
                wishlist.getProduct().getBrandName(),
                wishlist.getProduct().getProductPrice(),
                wishlist.getProduct().getProductThumbnail(),
                likeCount
        );
    }

}
