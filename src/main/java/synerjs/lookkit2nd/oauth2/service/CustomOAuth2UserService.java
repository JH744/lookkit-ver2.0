package synerjs.lookkit2nd.oauth2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = (String) attributeMap.get("email");
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerUser(attributeMap));
        return oAuth2User;
    }


    private User registerUser(Map<String, Object> attributeMap) {
        String email = (String) attributeMap.get("email");
        String userName = (String) attributeMap.get("name");
       String userUuid =((String) ((Map) attributeMap.get("profile")).get("nickname"));

        String phone = (String) attributeMap.get("phone_number");
        String genderStr = (String) attributeMap.get("gender");

        String gender = null;
        if (genderStr != null) {
            gender = genderStr.equals("female")? "F":"M";
        }

        User user = User.builder()
                .email(email)
                .userUuid(userUuid)
                .userName(userName)
                .phone(phone)
                .gender(gender)
                .birthDate(getBirthDate(attributeMap))
                .role("USER") // 기본값 설정
                .address((String) attributeMap.get("address")) // address가 있으면 추가
                .build();

        return userRepository.save(user);
    }

    private LocalDate getBirthDate(Map<String, Object> attributeMap) {
        String birthYear = (String) attributeMap.get("birthyear");
        String birthDay = (String) attributeMap.get("birthday");

        if (birthYear == null || birthDay == null || (birthYear + birthDay).length() != 8) {
            throw new IllegalArgumentException("Invalid birth date format");
        }
        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }
}