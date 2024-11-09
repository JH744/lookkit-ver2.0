package synerjs.lookkit2nd.review;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.coordiset.Coordiset;
import synerjs.lookkit2nd.product.Product;
import synerjs.lookkit2nd.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    private Coordiset coordiset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating; // 1~5점

    private String reviewText;

    private Timestamp createdAt;

    @Builder
    public Review(Product product, Coordiset coordiset, User user, Integer rating, String reviewText, Timestamp createdAt) {
        this.product = product;
        this.coordiset = coordiset;
        this.user = user;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
    }
}
