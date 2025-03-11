package synerjs.lookkit2nd.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiRepository;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;
import synerjs.lookkit2nd.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CodiRepository codiRepository;
    private final ReviewImageRepository reviewImageRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, CodiRepository codiRepository, ReviewImageRepository reviewImageRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.codiRepository = codiRepository;
        this.reviewImageRepository = reviewImageRepository;
    }

    // @Transactional
    // public void saveReview(ReviewDTO reviewDTO, User user) {
    //     Product product = null;
    //     Codi codi = null;

    //     if (reviewDTO.getProductId() != null) {
    //         product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
    //     }

    //     if (reviewDTO.getCodiId() != null) {
    //         codi = codiRepository.findById(reviewDTO.getCodiId()).orElse(null);
    //     }

    //     Review review = Review.builder()
    //             .product(product)
    //             .codi(codi)
    //             .user(user)
    //             .rating(reviewDTO.getRating())
    //             .reviewText(reviewDTO.getReviewText())
    //             .createdAt(new Timestamp(System.currentTimeMillis()))
    //             .build();

    //     reviewRepository.save(review);
    // }

    @Transactional
public void saveReview(ReviewDTO reviewDTO, User user, List<MultipartFile> imageFiles) {
    Product product = null;
    Codi codi = null;

    if (reviewDTO.getProductId() != null) {
        product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
    }

    if (reviewDTO.getCodiId() != null) {
        codi = codiRepository.findById(reviewDTO.getCodiId()).orElse(null);
    }

    Review review = Review.builder()
            .product(product)
            .codi(codi)
            .user(user)
            .rating(reviewDTO.getRating())
            .reviewText(reviewDTO.getReviewText())
            .createdAt(new Timestamp(System.currentTimeMillis()))
            .build();

    reviewRepository.save(review);

    // 리뷰 이미지 저장
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<ReviewImage> reviewImages = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                String imagePath = uploadToFirebase(file); // Firebase 업로드 메서드 사용
                ReviewImage reviewImage = ReviewImage.builder()
                        .review(review)
                        .imagePath(imagePath)
                        .build();
                reviewImages.add(reviewImage);
            }
            reviewImageRepository.saveAll(reviewImages);
        }
   }

    private String uploadToFirebase(MultipartFile file) {
        // Firebase에 파일을 업로드하고 URL을 반환하는 로직 구현
        // 예: return firebaseStorageService.upload(file);
        return "uploaded_file_url";
    }



@Transactional
public void deleteReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + reviewId));
    reviewRepository.delete(review);
}

public List<ReviewDTO> getReviewsByProductId(Long productId) {
    return reviewRepository.findByProductProductId(productId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

public List<ReviewDTO> getReviewsByCodiId(Long codiId) {
    return reviewRepository.findByCodiCodiId(codiId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

public List<ReviewDTO> getReviewsByUserId(Long userId) {
    return reviewRepository.findByUserUserId(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}


private ReviewDTO convertToDTO(Review review) {
    Product product = review.getProduct() != null ? productRepository.findById(review.getProduct().getProductId()).orElse(null) : null;
    Codi codi = review.getCodi() != null ? codiRepository.findById(review.getCodi().getCodiId()).orElse(null) : null;
    return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProduct() != null ? review.getProduct().getProductId() : null)
                .codiId(review.getCodi() != null ? review.getCodi().getCodiId() : null)
                .userId(review.getUser().getUserId())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .createdAt(review.getCreatedAt())
                .productName(product != null ? product.getProductName() : null)
                .brandName(product != null ? product.getBrandName() : null)
                .codiDescription(codi != null ? codi.getCodiDescription() : null)
                .build();
}


    // public List<ReviewDTO> getReviewsByProductId(Long productId) {
    //     return reviewRepository.findByProductProductId(productId).stream()
    //             .map(review -> ReviewDTO.builder()
    //                     .reviewId(review.getReviewId())
    //                     .productId(review.getProduct() != null ? review.getProduct().getProductId() : null)
    //                     .codiId(review.getCodi() != null ? review.getCodi().getCodiId() : null)
    //                     .userId(review.getUser().getUserId())
    //                     .rating(review.getRating())
    //                     .reviewText(review.getReviewText())
    //                     .createdAt(review.getCreatedAt())
    //                     .build())
    //             .collect(Collectors.toList());
    // }

    // public List<ReviewDTO> getReviewsByCodiId(Long codiId) {
    //     return reviewRepository.findByCodiCodiId(codiId).stream()
    //             .map(review -> ReviewDTO.builder()
    //                     .reviewId(review.getReviewId())
    //                     .productId(review.getProduct() != null ? review.getProduct().getProductId() : null)
    //                     .codiId(review.getCodi() != null ? review.getCodi().getCodiId() : null)
    //                     .userId(review.getUser().getUserId())
    //                     .rating(review.getRating())
    //                     .reviewText(review.getReviewText())
    //                     .createdAt(review.getCreatedAt())
    //                     .build())
    //             .collect(Collectors.toList());
    // }
}