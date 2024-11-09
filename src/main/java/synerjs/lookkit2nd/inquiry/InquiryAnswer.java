package synerjs.lookkit2nd.inquiry;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "inquiries_answer")
@ToString
public class InquiryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    private String answerContents;

    private Timestamp answerCreatedAt;

    @Builder
    public InquiryAnswer(Inquiry inquiry, String answerContents, Timestamp answerCreatedAt) {
        this.inquiry = inquiry;
        this.answerContents = answerContents;
        this.answerCreatedAt = answerCreatedAt;
    }
}
