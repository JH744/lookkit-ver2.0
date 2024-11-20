package synerjs.lookkit2nd.wishlist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.wishlist.dto.WishlistRequestDTO;
import synerjs.lookkit2nd.wishlist.dto.WishlistResponseDTO;
import synerjs.lookkit2nd.wishlist.service.WishlistService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    public BaseResponse<List<WishlistResponseDTO>> getWishlist(@PathVariable Long userId) {
        List<WishlistResponseDTO> response = wishlistService.getWishlist(userId);
        return new BaseResponse<>(response);
    }

    @DeleteMapping("/{wishlistId}")
    public BaseResponse<Long> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWish(wishlistId);
        return new BaseResponse<>(wishlistId);
    }

    @PostMapping("/{userId}")
    public BaseResponse<WishlistResponseDTO> addWishlist(@PathVariable Long userId, @RequestBody WishlistRequestDTO request) {
        WishlistResponseDTO response = wishlistService.addWish(userId, request);
        return new BaseResponse<>(response);
    }

}
