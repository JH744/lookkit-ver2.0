package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.ToString;

@ToString
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> account;
    private final Map<String, Object> attributes;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attributes = attribute;
        this.account = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id")); // Long -> String 변환
    }

    @Override
    public String getUserUuid() {
        return "kakao_" + attributes.get("id");
    }

    @Override
    public String getGender() {
        String genderRaw = (String) account.get("gender");
        String gender =
            "male".equalsIgnoreCase(genderRaw) ? "M" : "F"; // 리팩토링 필요!
        return gender;
    }

    @Override
    public LocalDate getBirthDate() {
        String birthYear = (String) account.get("birthyear");
        String birthDay = (String) account.get("birthday");
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
        return birthDate;
    }

    @Override
    public String getPhone() {
        String phone = (String) account.get("phone_number");
        if (phone == null || phone.isEmpty()) {
            phone = "미제공";
        }
        return phone;
    }

    @Override
    public String getEmail() {
        return account.get("email").toString();
    }

    @Override
    public String getName() {
        return account.get("name").toString();
    }
}