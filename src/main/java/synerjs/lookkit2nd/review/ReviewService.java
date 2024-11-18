package synerjs.lookkit2nd.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiRepository;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.product.ProductRepository;
import synerjs.lookkit2nd.user.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CodiRepository codiRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, CodiRepository codiRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.codiRepository = codiRepository;
    }

    @Transactional
    public void saveReview(ReviewDTO reviewDTO, User user) {
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
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductProductId(productId).stream()
                .map(review -> ReviewDTO.builder()
                        .reviewId(review.getReviewId())
                        .productId(review.getProduct() != null ? review.getProduct().getProductId() : null)
                        .codiId(review.getCodi() != null ? review.getCodi().getCodiId() : null)
                        .userId(review.getUser().getUserId())
                        .rating(review.getRating())
                        .reviewText(review.getReviewText())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByCodiId(Long codiId) {
        return reviewRepository.findByCodiCodiId(codiId).stream()
                .map(review -> ReviewDTO.builder()
                        .reviewId(review.getReviewId())
                        .productId(review.getProduct() != null ? review.getProduct().getProductId() : null)
                        .codiId(review.getCodi() != null ? review.getCodi().getCodiId() : null)
                        .userId(review.getUser().getUserId())
                        .rating(review.getRating())
                        .reviewText(review.getReviewText())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}