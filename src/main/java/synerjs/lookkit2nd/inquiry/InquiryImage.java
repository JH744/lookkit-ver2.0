package synerjs.lookkit2nd.inquiry;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "inquiries_images")
public class InquiryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    private String imagePath;

    @Builder
    public InquiryImage(Inquiry inquiry, String imagePath) {
        this.inquiry = inquiry;
        this.imagePath = imagePath;
    }
}
