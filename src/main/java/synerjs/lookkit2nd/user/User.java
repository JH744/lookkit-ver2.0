package synerjs.lookkit2nd.user;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.order.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID") // 컬럼명 명시
    private Long userId;

    @Column(name = "USER_UUID") // 컬럼명 명시
    private String userUid;

    @Column(name = "PASSWORD") // 컬럼명 명시 (필요 시)
    private String password;

    @Column(name = "USER_NAME") // 컬럼명 명시
    private String userName;

    @Column(name = "GENDER") // 컬럼명 명시 (필요 시)
    private String gender;

    @Column(name = "BIRTHDATE") // 컬럼명 명시
    private LocalDate birthDate;

    @Column(name = "EMAIL") // 컬럼명 명시 (필요 시)
    private String email;

    @Column(name = "PHONE") // 컬럼명 명시 (필요 시)
    private String phone;

    @Column(name = "CREATED_AT") // 컬럼명 명시
    private Timestamp userCreatedAt;

    @Column(name = "LAST_UPDATE") // 컬럼명 명시 (필요 시)
    private Timestamp lastUpdate;

    @Column(name = "ROLE") // 컬럼명 명시 (필요 시)
    private String role;

    @Column(name = "ADDRESS") // 컬럼명 명시 (필요 시)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String userUid, String password, String userName, String gender, LocalDate birthDate,
                String email, String phone, Timestamp userCreatedAt, String role, String address) {
        this.userUid = userUid;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.userCreatedAt = userCreatedAt;
        this.role = role;
        this.address = address;
    }
}
