package synerjs.lookkit2nd.user;


import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long userId;
    private String userUuid;
    private String userName;
    private String password;
    private String gender;
    @DateTimeFormat(pattern = "yyMMdd")
    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;
    private String email;
    private Timestamp createdAt;
    private String phone;
    private Timestamp lastUpdate;
    private String role;
    private String address;

}
