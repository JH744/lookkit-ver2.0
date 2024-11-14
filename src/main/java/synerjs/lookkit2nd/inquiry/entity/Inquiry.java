package synerjs.lookkit2nd.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private LocalDateTime inquiryCreatedAt;

    private char answerState; // Enum 대신 char 사용
}
