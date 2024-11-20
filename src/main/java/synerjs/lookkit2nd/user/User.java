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

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID") // 컬럼명 명시
    private Long userId;


    @Column(name = "USER_UUID") // 컬럼명 명시
    private String userUuid;

    @Column(name = "PASSWORD") // 컬럼명 명시 (필요 시)
    private String password;

    @Column(name = "USER_NAME") // 컬럼명 명시
    private String userName;

    @Column(name = "GENDER") // 컬럼명 명시 (필요 시)
    private String gender;


    @DateTimeFormat(pattern = "yyMMdd")
    @Column(name = "BIRTHDATE") // 컬럼명 명시
    private LocalDate birthDate;

    @Column(name = "EMAIL") // 컬럼명 명시 (필요 시)
    private String email;

    @Column(name = "PHONE") // 컬럼명 명시 (필요 시)
    private String phone;
    // @Column(name = "CREATED_AT")

    @CreatedDate
        @Column(name = "CREATED_AT", updatable = false) // 컬럼명 명시
    private Timestamp userCreatedAt;

    @Column(name = "LAST_UPDATE") // 컬럼명 명시 (필요 시)
    @LastModifiedDate
    private Timestamp lastUpdate;

    @Column(name = "ROLE") // 컬럼명 명시 (필요 시)
    private String role;

    @Column(name = "ADDRESS") // 컬럼명 명시 (필요 시)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String userUuid, String password, String userName, String gender, LocalDate birthDate,
                String email, String phone, Timestamp userCreatedAt,Timestamp lastUpdate, String role, String address) {
        this.userUuid = userUuid;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.userCreatedAt = userCreatedAt;
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