package synerjs.lookkit2nd.oauth2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class OAuth2Attributes {

    private final String userUuid;
    private final String userName;
    private final String gender;
    private final String email;
    private final String phone;
    private final String address;
    private final LocalDate birthDate;

    // private 생성자
    private OAuth2Attributes(String userUuid,
                             String userName,
                             String gender,
                             String email,
                             String phone,
                             String address,
                             LocalDate birthDate) {
        this.userUuid = userUuid;
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
    }

    /**
     * 카카오 attributes를 받아 필요한 필드를 추출
     * 카카오 attributes 예시:
     * {
     *   "id": 1234567890,
     *   "kakao_account": {
     *     "name": "홍길동",
     *     "email": "example@kakao.com",
     *     "gender": "male",
     *     "birthyear": "1994",
     *     "birthday": "0310",
     *     "phone_number": "+82 10-3914-5060"
     *     ...
     *   }
     * }
     */
    @SuppressWarnings("unchecked")
    public static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
        Long kakaoId = (Long) attributes.get("id");
        String userUuid = (kakaoId != null) ? "kakao_" + kakaoId : "kakao_unknown";

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount == null) {
            kakaoAccount = Map.of();
        }

        // 이름
        String name = (String) kakaoAccount.get("name");
        if (name == null || name.isEmpty()) {
            name = "카카오사용자";
        }

        // 성별
        String genderRaw = (String) kakaoAccount.get("gender");
        String gender = "U";
        if ("male".equalsIgnoreCase(genderRaw)) {
            gender = "M";
        } else if ("female".equalsIgnoreCase(genderRaw)) {
            gender = "F";
        }

        // 이메일
        String rawEmail = (String) kakaoAccount.get("email");
        String email = (rawEmail != null && !rawEmail.isEmpty()) ? "kakao_" + rawEmail : "kakao_noemail_" + userUuid;

        // 전화번호
        String phone = (String) kakaoAccount.get("phone_number");
        if (phone == null || phone.isEmpty()) {
            phone = "미제공";
        }

        // 주소 고정값
        String address = "미제공";

        // 생년월일
        String birthYear = (String) kakaoAccount.get("birthyear");
        String birthDay = (String) kakaoAccount.get("birthday");
        LocalDate birthDate = null;
        if (birthYear != null && !birthYear.isEmpty() && birthDay != null && !birthDay.isEmpty()) {
            try {
                String dateString = birthYear + birthDay; // yyyyMMdd
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                birthDate = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                // 파싱 실패시 null 유지
            }
        }

        return new OAuth2Attributes(
                userUuid,
                name,
                gender,
                email,
                phone,
                address,
                birthDate
        );
    }

    // Getter 메서드들
    public String getUserUuid() {
        return userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    // toMap() 메소드: 객체 데이터를 Map으로 변환
    public Map<String, Object> toMap() {
        Map<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("name", userName);
        attributesMap.put("email", email);
        attributesMap.put("userUuid", userUuid);
        attributesMap.put("gender", gender);
        attributesMap.put("birthDate", birthDate);
        attributesMap.put("phone", phone);
        return attributesMap;
    }
}