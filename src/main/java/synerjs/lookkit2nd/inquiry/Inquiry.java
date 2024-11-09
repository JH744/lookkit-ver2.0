package synerjs.lookkit2nd.inquiry;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String inquiryTitle;

    private String inquiryContents;

    private Timestamp inquiryCreatedAt;

    private String answerState; // Enum 대신 String 사용

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InquiryImage> inquiryImages = new ArrayList<>();

    @Builder
    public Inquiry(User user, String inquiryTitle, String inquiryContents, Timestamp inquiryCreatedAt, String answerState) {
        this.user = user;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContents = inquiryContents;
        this.inquiryCreatedAt = inquiryCreatedAt;
        this.answerState = answerState;
    }
}
