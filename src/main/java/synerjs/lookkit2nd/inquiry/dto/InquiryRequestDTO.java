package synerjs.lookkit2nd.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryRequestDTO {
    private Long userId;
    private String inquiryTitle;
    private String inquiryContents;

    public Inquiry toEntity() {
        return Inquiry.builder()
                .userId(this.userId)
                .inquiryTitle(this.inquiryTitle)
                .inquiryContents(this.inquiryContents)
                .build();
    }
}
