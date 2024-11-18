package synerjs.lookkit2nd.codi;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.review.Review;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "codi")
public class Codi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codiId;

    private String codiName;
    private String codiDescription;
    private String codiThumbnail;
    private Integer codiPrice;

    @OneToMany(mappedBy = "codi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Codi(String codiName, String codiDescription, String codiThumbnail, Integer codiPrice) {
        this.codiName = codiName;
        this.codiDescription = codiDescription;
        this.codiThumbnail = codiThumbnail;
        this.codiPrice = codiPrice;
    }
}


