package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.ToString;

@ToString
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
        // System.out.println("네이버 >> "+(Map<String, Object>) attribute.get("response"));
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getUserUuid() {
        return "naver_" + attribute.get("id");
    }

    @Override
    public String getGender() {
        return attribute.get("gender").toString();
    }

    @Override
    public LocalDate getBirthDate() {
        String birthYear = attribute.get("birthyear").toString();
        String birthDay = attribute.get("birthday").toString();
        String birthDate = birthYear + "-" + birthDay;
        return LocalDate.parse(birthDate);
    }

    @Override
    public String getPhone() {
        return attribute.get("mobile").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}