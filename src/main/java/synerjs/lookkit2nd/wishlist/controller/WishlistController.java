package synerjs.lookkit2nd.wishlist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synerjs.lookkit2nd.common.response.BaseResponse;
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
}
