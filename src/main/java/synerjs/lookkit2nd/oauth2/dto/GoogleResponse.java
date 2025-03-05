package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;
import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getUserUuid() {
        return "google_" + attribute.get("sub");
    }

    //구글  기본정보외 미제공
    @Override
    public String getGender() {
        return "M";
    }

    @Override
    public LocalDate getBirthDate() {
        //미제공
        String birthDate = "1999-01-01";
        return LocalDate.parse(birthDate);
    }

    @Override
    public String getPhone() {
        //미제공
        return "000-0000-0000";
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