package synerjs.lookkit2nd.mypage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import synerjs.lookkit2nd.user.User;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageDTO {
    private long userId;
    private String userUuid;

    @NotEmpty(message = "이름은 필수입니다.")
    private String userName;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "주소는 필수입니다.")
    private String address;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .phone(phone)
                .birthDate(birthDate)
                .email(email)
                .address(address)
                .build();
    }
}
