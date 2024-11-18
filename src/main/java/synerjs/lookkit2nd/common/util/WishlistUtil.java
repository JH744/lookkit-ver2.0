package synerjs.lookkit2nd.common.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiRepository;
import synerjs.lookkit2nd.common.exception.BaseException;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;
import synerjs.lookkit2nd.wishlist.dto.WishlistRequestDTO;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;
import synerjs.lookkit2nd.wishlist.repository.WishlistRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class WishlistUtil {
    private final WishlistRepository repository;
    private final ProductRepository productRepository;
    private final CodiRepository codiRepository;

    @Transactional
    public Wishlist createWishlist(Long userId, WishlistRequestDTO request) {
        // 기존에 같은 사용자가 같은 상품에 대한 좋아요가 있는지 확인
        Optional<Wishlist> existingWish = repository.findByUserIdAndProduct_ProductId(userId, request.getProductId());
        if (existingWish.isPresent()) {
            throw new BaseException(BaseResponseStatus.WISH_ALREADY_EXISTS);
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PRODUCT_NOT_FOUND));

        Codi codi = request.getCodiId() != null ?
                codiRepository.findById(request.getCodiId()).orElse(null) : null;

        return request.toEntity(product, codi, userId);
    }
}
