package synerjs.lookkit2nd.oauth2.dto;

import java.time.LocalDate;
import java.util.Map;

public class GoogleResponse implements OAuth2Response{

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