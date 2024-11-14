package synerjs.lookkit2nd.user;

import jakarta.persistence.*;
import lombok.*;
import synerjs.lookkit2nd.order.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userUid;
    private String password;
    private String userName;
    private String gender;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private Timestamp userCreatedAt;
    private Timestamp lastUpdate;
    private String role;
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

//    @Builder
//    public User(String userUid, String password, String userName, String gender, LocalDate birthDate,
//                String email, String phone, Timestamp userCreatedAt, String role, String address) {
//        this.userUid = userUid;
//        this.password = password;
//        this.userName = userName;
//        this.gender = gender;
//        this.birthDate = birthDate;
//        this.email = email;
//        this.phone = phone;
//        this.userCreatedAt = userCreatedAt;
//        this.role = role;
//        this.address = address;
//    }
}
