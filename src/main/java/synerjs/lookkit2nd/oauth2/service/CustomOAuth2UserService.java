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

@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성
@Service // Spring 서비스 클래스
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 loadUser 메서드를 호출하여 OAuth2User 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2User에서 카카오 계정 정보를 가져옴
        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = (String) attributeMap.get("email");

        // 이메일을 기준으로 기존 사용자를 조회. 없으면 새로운 사용자 등록
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerUser(attributeMap));

        // 반환된 OAuth2User를 그대로 반환 (추후 필요시 확장 가능)
        return oAuth2User;
    }

    // 새로운 사용자를 등록하는 메서드
    private User registerUser(Map<String, Object> attributeMap) {
        // 사용자 정보 추출
        String email = (String) attributeMap.get("email");
        String userName = (String) attributeMap.get("name");
        String userUuid = ((String) ((Map) attributeMap.get("profile")).get("nickname"));
        String phone = (String) attributeMap.get("phone_number");
        String genderStr = (String) attributeMap.get("gender");

        // 성별 변환 (female -> F, male -> M)
        String gender = null;
        if (genderStr != null) {
            gender = genderStr.equals("female") ? "F" : "M";
        }

        // User 엔티티 빌더 패턴을 사용하여 객체 생성
        User user = User.builder()
                .email(email)
                .userUuid(userUuid)
                .userName(userName)
                .phone(phone)
                .gender(gender)
                .birthDate(getBirthDate(attributeMap)) // 생년월일 계산
                .role("USER") // 기본 역할 설정
                .address((String) attributeMap.get("address")) // 주소 설정 (있으면)
                .build();

        // 생성된 User 객체를 데이터베이스에 저장
        return userRepository.save(user);
    }

    // 생년월일을 LocalDate로 변환하는 메서드
    private LocalDate getBirthDate(Map<String, Object> attributeMap) {
        String birthYear = (String) attributeMap.get("birthyear");
        String birthDay = (String) attributeMap.get("birthday");

        // 생년월일 데이터가 유효하지 않으면 예외 발생
        if (birthYear == null || birthDay == null || (birthYear + birthDay).length() != 8) {
            throw new IllegalArgumentException("Invalid birth date format");
        }

        // "yyyyMMdd" 형식의 문자열을 LocalDate로 변환
        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }
}