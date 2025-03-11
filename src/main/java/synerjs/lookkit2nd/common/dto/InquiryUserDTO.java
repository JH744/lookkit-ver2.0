package synerjs.lookkit2nd.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InquiryUserDTO {
    private Long inquiryId;
    private String userUuid;
    private String inquiryTitle;
    private String inquiryContents;
    private LocalDateTime inquiryCreatedAt;
    private String answerState;

    public InquiryUserDTO(Long inquiryId, String userUuid, String inquiryTitle,
                          String inquiryContents, LocalDateTime inquiryCreatedAt,
                          String answerState) {
        this.inquiryId = inquiryId;
        this.userUuid = userUuid;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContents = inquiryContents;
        this.inquiryCreatedAt = inquiryCreatedAt;
        this.answerState = answerState;
    }

    // Getters
}