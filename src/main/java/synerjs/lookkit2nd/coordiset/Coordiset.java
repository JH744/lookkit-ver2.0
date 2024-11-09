package synerjs.lookkit2nd.coordiset;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.review.Review;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "codi")
public class Coordiset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codiId;

    private String codiName;

    private String codiDescription;

    private String codiThumbnail;

    private Integer codiPrice;

    @OneToMany(mappedBy = "coordiset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Coordiset(String codiName, String codiDescription, String codiThumbnail, Integer codiPrice) {
        this.codiName = codiName;
        this.codiDescription = codiDescription;
        this.codiThumbnail = codiThumbnail;
        this.codiPrice = codiPrice;
    }
}
