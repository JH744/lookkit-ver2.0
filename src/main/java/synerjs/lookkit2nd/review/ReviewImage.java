package synerjs.lookkit2nd.review;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "REVIEWS_IMAGES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_IMAGE_ID")
    private Long reviewImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID", nullable = false)
    private Review review;

    @Column(name = "IMAGE_PATH", nullable = false)
    private String imagePath;
}