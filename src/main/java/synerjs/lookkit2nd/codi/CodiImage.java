package synerjs.lookkit2nd.codi;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "codi_images")
@ToString
public class CodiImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codiImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODI_ID")
    private Codi codi;

    private String imgPath;

    @Builder
    public CodiImage(Codi codi, String imgPath) {
        this.codi = codi;
        this.imgPath = imgPath;
    }
}