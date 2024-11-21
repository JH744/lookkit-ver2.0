package synerjs.lookkit2nd.inquiry.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inquiries_answer")
@ToString
public class InquiryAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    @JsonIgnore // 순환 참조 방지
    private Inquiry inquiry;

    private String answerContents;
    @CreatedDate
    private Timestamp answerCreatedAt;

    @Builder
    public InquiryAnswer(Inquiry inquiry, String answerContents, Timestamp answerCreatedAt) {
        this.inquiry = inquiry;
        this.answerContents = answerContents;
        this.answerCreatedAt = answerCreatedAt;
    }
}
