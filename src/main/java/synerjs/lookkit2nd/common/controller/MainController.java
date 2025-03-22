package synerjs.lookkit2nd.common.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synerjs.lookkit2nd.codi.CodiDTO;
import synerjs.lookkit2nd.codi.CodiService;
import synerjs.lookkit2nd.inquiry.dto.CodiProductDTO;
import synerjs.lookkit2nd.product.ProductDTO;
import synerjs.lookkit2nd.product.ProductService;
import synerjs.lookkit2nd.wishlist.dto.WishlistRequestDTO;
import synerjs.lookkit2nd.wishlist.service.WishlistService;

@RestController
@RequestMapping("/api/main/")
@RequiredArgsConstructor
public class MainController {
    private final CodiService coordisetService;
    private final ProductService productService;
    private final WishlistService wishlistService;


    // 모든 코디 반환
    @GetMapping("/codis/all")
    public ResponseEntity<List<CodiDTO>> getLatestEightCodiSets(Authentication auth) {
        return ResponseEntity.ok(coordisetService.getCodiSets());
    }

    // 코디 세트 & 연관상품 조회
    @GetMapping("/codiset")
    public ResponseEntity<List<CodiProductDTO>> getAllCoordiWithProducts() {
        List<CodiProductDTO> products = coordisetService.getAllCoordiWithProducts();

        return ResponseEntity
            .ok()
            .cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS)) // 1시간 캐싱 유지
            .body(products);
    }

    // 카테고리 별 상품 목록 표시
    @GetMapping("/category")
    public ResponseEntity<List<ProductDTO>> mainCategoryPage(
            @RequestParam String type,
            @RequestParam(required = false) String sort) {
        System.out.println("type: " + type);
        System.out.println("sort: " + sort);
        List<ProductDTO> products = productService.getProductsByCategory(type, sort);
        return ResponseEntity.ok(products);
    }

    //검색 결과 페이지
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> mainSearchPage(@RequestParam String keyword) {
//        System.out.println("키워드: " + keyword);
        List<ProductDTO> productsList = productService.searchProductsByKeyword(keyword);
        return ResponseEntity.ok(productsList);
    }

    // 상품 찜 상태 확인
    @PostMapping("/checkBatch/{userId}")
    public ResponseEntity<Map<String, Object>> checkItemsInWishlist(
            @PathVariable Long userId,
            @RequestBody List<Long> itemIds) {
        List<Long> wishlistItemIds = wishlistService.getWishlistItemIds(userId, itemIds);

        Map<String, Object> response = new HashMap<>();
        response.put("wishlistItemIds", wishlistItemIds);
        return ResponseEntity.ok(response);
    }

    // 상품 찜 삭제
    @PostMapping("/wishlist/delete/{userId}")
    public ResponseEntity<Void> deleteWishlistByProductId(@PathVariable Long userId, @RequestBody WishlistRequestDTO request) {
        wishlistService.deleteWishByProductId(userId, request);
        return ResponseEntity.noContent().build(); //204 성공으로 응답(반환데이터는 없음)
    }

    // 코디 찜상태 확인
    @GetMapping("/checkBatchCodi/{userId}/{codiId}")
    public ResponseEntity<Boolean> checkWishlistForCodi(
            @PathVariable Long userId,
            @PathVariable Long codiId) {
        boolean isWishlisted = wishlistService.isCodiInWishlist(userId, codiId);
        return ResponseEntity.ok(isWishlisted);


    }

    // 코디 위시리스트 추가
    @PostMapping("/coordi/wish/add")
    public ResponseEntity<String> addWishlist(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long codiId = request.get("codiId");

        if (userId == null || codiId == null) {
            throw new IllegalArgumentException("userId and codiId are required");
        }
        wishlistService.addWishlist(userId, codiId);
        return ResponseEntity.ok("Wishlist added successfully");
    }
}









