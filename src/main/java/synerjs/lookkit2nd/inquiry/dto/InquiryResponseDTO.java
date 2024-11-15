package synerjs.lookkit2nd.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class InquiryResponseDTO {
    private Long inquiryId;
    private Long userId;
    private String inquiryTitle;
    private String inquiryContents;
    private LocalDateTime inquiryCreatedAt;
    private String answerState;
    private List<InquiryImageDTO> inquiryImages = new ArrayList<>();

    public static InquiryResponseDTO fromEntity(Inquiry inquiry, List<InquiryImageDTO> inquiryImages) {
        return InquiryResponseDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .userId(inquiry.getUserId())
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContents(inquiry.getInquiryContents())
                .inquiryCreatedAt(inquiry.getInquiryCreatedAt())
                .answerState(inquiry.getAnswerState())
                .inquiryImages(inquiryImages)
                .build();
    }
}
