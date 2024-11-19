package synerjs.lookkit2nd.common.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiDTO;
import synerjs.lookkit2nd.codi.CodiService;
import synerjs.lookkit2nd.inquiry.dto.CodiProductDTO;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductDTO;
import synerjs.lookkit2nd.product.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/main/")
@RequiredArgsConstructor
public class MainController {
    private final CodiService coordisetService;
    private final ProductService productService;


    // 모든 코디 반환
    @GetMapping("/codis/all")
    public ResponseEntity<List<CodiDTO>> getLatestEightCodiSets() {
        return ResponseEntity.ok(coordisetService.getCodiSets());
    }

    /**
     * 코디 세트와 연관된 상품 10개 조회 API
     */
    @GetMapping("/codiset")
    public ResponseEntity<List<CodiProductDTO>> getAllCoordiWithProducts() {
        return ResponseEntity.ok(coordisetService.getAllCoordiWithProducts());
    }


    // 카테고리 별 상품 목록 표시
    @GetMapping("/category")
    public ResponseEntity<List<ProductDTO>> mainCategoryPage(@RequestParam String type) {
        System.out.println("카테고리: " + type);
      return  ResponseEntity.ok(productService.getProductsByCategory(type));
    }


//
//    @GetMapping("/main/search")
//    public String mainSearchPage(Model model, @RequestParam String keyword){
//        System.out.println("키워드: "+keyword);
//        List<ProductVO> productsList= productService.searchProductsByKeyword(keyword);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("productsList", productsList);
//        return "/home/search";
//    }
//


//    // 좋아요 추가 및 삭제
//    @GetMapping("/wishlist/item")
//    @ResponseBody
//    public Map<String, Object> addWishList(@RequestParam long productId, HttpSession session){
//        Map<String, Object> response = new HashMap<>();
//        long userId = (long) session.getAttribute("userid");
//        System.out.println("위시리스트컨트롤러 동작");
//        WishlistVO wishlistVO = new WishlistVO();
//        wishlistVO.setUserId(userId);
//        wishlistVO.setProductId(productId);
//        wishlistVO.setCodiId(null);
//        String result=wishlistService.addWishlistItem(wishlistVO);
//
//        if ("addOK".equals(result)) {
//            response.put("status", "success");
//            response.put("message", "상품추가");
//        } else if ("deleteOK".equals(result)) {
//            response.put("status", "success");
//            response.put("message", "상품삭제");
//        } else {
//            response.put("status", "error");
//            response.put("message", "오류발생");
//        }
//        return response;
//    }
//
//    // 좋아요된 상품확인
//    @GetMapping("/wishlist/check")
//    @ResponseBody
//    public Map<String, Object> checkWishItem(@RequestParam("itemId") long productId, HttpSession session) {
//        System.out.println("전달된 상품id:"+productId);
//        Map<String, Object> response = new HashMap<>();
//        WishlistVO wishlistVO = new WishlistVO();
//        long userId = (long) session.getAttribute("userid");
//        wishlistVO.setUserId(userId);
//        wishlistVO.setProductId(productId);
//        String result= wishlistService.checkWishItem(wishlistVO);
//        System.out.println(productId+"-result:"+result);
//        response.put("status", "success");
//        response.put("message",result);
//        return response;
//    }




}
