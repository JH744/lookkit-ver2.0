package synerjs.lookkit2nd.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import synerjs.lookkit2nd.order.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userUuid;
    private String userName;
    private String password;
    private String gender;
    @DateTimeFormat(pattern = "yyMMdd")
    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;
    private String email;
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdAt;
    private String phone;
    @LastModifiedDate
    private Timestamp lastUpdate;
    private String role;
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String userUuid, String password, String userName, String gender, LocalDate birthDate,
                String email, String phone, Timestamp createdAt,Timestamp lastUpdate, String role, String address) {
        this.userUuid = userUuid;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.lastUpdate=lastUpdate;
        this.role = role != null ? role : "USER"; //빌더 사용시 기본값은 일반유저
        this.address = address;
    }
    @PrePersist // 엔티티가 저장되기 전에 role 값이 null이라면 'USER'를 기본값 설정
    private void setDefaultRole() {
        if (this.role == null) {
            this.role = "USER";
        }
    }

}
