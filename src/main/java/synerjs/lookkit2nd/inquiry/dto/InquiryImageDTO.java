package synerjs.lookkit2nd.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;
import synerjs.lookkit2nd.inquiry.entity.InquiryImage;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryImageDTO {
    private Long inquiryImageId;
    private Long inquiryId;
    private String imagePath;

    public InquiryImage toEntity(Inquiry inquiry) {
        return InquiryImage.builder()
                .inquiryImageId(this.inquiryImageId) // 만약 새로 생성하는 이미지라면 null로 설정될 수 있음
                .inquiryId(this.inquiryId)
                .imagePath(this.imagePath)
                .build();
    }

    public static InquiryImageDTO fromEntity(InquiryImage image) {
        return new InquiryImageDTO(
                image.getInquiryImageId(),
                image.getInquiryId(),
                image.getImagePath()
        );
    }
}
