package synerjs.lookkit2nd.wishlist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synerjs.lookkit2nd.wishlist.service.WishlistService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

//    @GetMapping("/{userId}")
//    public BaseResponse<List<WishlistResponseDTO>> getWishlist(@PathVariable Long userId) {
//        List<WishlistResponseDTO> response = wishlistService.getWishlist(userId);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>응답:  " + response);
//        return new BaseResponse<>(response);
//    }
}
