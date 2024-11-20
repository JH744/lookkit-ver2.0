package synerjs.lookkit2nd.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    // Product ID로 리뷰 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Codi ID로 리뷰 조회
    @GetMapping("/codi/{codiId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByCodiId(@PathVariable Long codiId) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByCodiId(codiId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            User user = userService.getUserById(reviewDTO.getUserId());
            reviewService.saveReview(reviewDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body("리뷰 작성 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 작성 실패");
        }
    }
}
