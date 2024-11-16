package synerjs.lookkit2nd.mypage;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PwChangeDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword; // 새로운 비밀번호 확인
}
