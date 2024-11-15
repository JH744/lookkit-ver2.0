package synerjs.lookkit2nd.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
@Table(name = "inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    private Long userId;

    private String inquiryTitle;

    private String inquiryContents;

    @CreationTimestamp
    private LocalDateTime inquiryCreatedAt;

    @Column(length = 1)
    @Builder.Default
    private String answerState = "N"; // Enum 대신 String
}
