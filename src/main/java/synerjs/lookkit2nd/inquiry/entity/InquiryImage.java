package synerjs.lookkit2nd.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "inquiries_images")
public class InquiryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryImageId;

    private Long inquiryId;

    private String imagePath;
}
