package synerjs.lookkit2nd.review;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class ReviewDTO {
    private Long reviewId;
    private Long productId;
    private Long codiId;
    private Long userId;
    private Integer rating;
    private String reviewText;
    private Timestamp createdAt;

    private String productName;
    private String brandName;
    private String codiDescription;
    

    public ReviewDTO(Long reviewId, Long productId, Long codiId, Long userId, Integer rating, String reviewText, Timestamp createdAt, String productName, String brandName, String codiDescription) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.codiId = codiId;
        this.userId = userId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
        this.productName = productName;
        this.brandName = brandName;
        this.codiDescription = codiDescription;
    }
}

