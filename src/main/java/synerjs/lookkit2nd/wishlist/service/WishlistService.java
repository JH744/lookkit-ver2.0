package synerjs.lookkit2nd.wishlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiRepository;
import synerjs.lookkit2nd.common.exception.BaseException;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;
import synerjs.lookkit2nd.wishlist.dto.WishlistRequestDTO;
import synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO;
import synerjs.lookkit2nd.wishlist.entity.Wishlist;
import synerjs.lookkit2nd.wishlist.repository.WishlistRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository repository;
    private final ProductRepository productRepository;
    private final CodiRepository codiRepository;

    public List<WishlistResponseDTO> getWishlist(Long userId) {
        return repository.getWishlistByUserId(userId);
    }

    @Transactional
    public void deleteWish(Long wishlistId) {
        repository.findById(wishlistId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.WISH_NOT_FOUND));
        repository.deleteById(wishlistId);
    }

    @Transactional
    public WishlistResponseDTO addWish(Long userId, WishlistRequestDTO request) {
        // 기존에 같은 사용자가 같은 상품에 대한 좋아요가 있는지 확인
        Optional<Wishlist> existingWish = repository.findByUserIdAndProduct_ProductId(userId, request.getProductId());
        if (existingWish.isPresent()) {
            throw new BaseException(BaseResponseStatus.WISH_ALREADY_EXISTS);
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PRODUCT_NOT_FOUND));

        Codi codi = request.getCodiId() != null ?
                codiRepository.findById(request.getCodiId())
                        .orElse(null) : null;

        Wishlist wishlist = request.toEntity(product, codi, userId);
        repository.saveAndFlush(wishlist);

        return repository.getWishByUserId(userId, request.getProductId());
    }
}
