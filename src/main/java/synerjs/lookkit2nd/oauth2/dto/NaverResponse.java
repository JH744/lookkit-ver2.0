package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;
import java.util.Map;
import lombok.ToString;

@ToString
public class NaverResponse implements OAuth2Response{
    private final Map<String, Object> attribute;
    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
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
        return "";
    }

    @Override
    public String getGender() {
        return "";
    }

    @Override
    public LocalDate getBirthDate() {
        return null;
    }

    @Override
    public String getPhone() {
        return "";
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