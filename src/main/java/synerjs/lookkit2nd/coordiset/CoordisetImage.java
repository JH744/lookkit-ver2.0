package synerjs.lookkit2nd.coordiset;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "codi_images")
@ToString
public class CoordisetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codiImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    private Coordiset coordiset;

    private String imgPath;

    @Builder
    public CoordisetImage(Coordiset coordiset, String imgPath) {
        this.coordiset = coordiset;
        this.imgPath = imgPath;
    }
}