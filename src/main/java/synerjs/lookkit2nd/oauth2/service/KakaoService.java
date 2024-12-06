package synerjs.lookkit2nd.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import synerjs.lookkit2nd.oauth2.AuthTokensGenerator;
import synerjs.lookkit2nd.oauth2.LoginResponse;
import synerjs.lookkit2nd.oauth2.token.AuthTokens;
import synerjs.lookkit2nd.oauth2.token.JwtTokenProvider;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${KAKAO_CLIENT_ID}")
    private String clientId;

    @Value("${KAKAO_REDIRECT_URL}")
    private String redirectUri;


    /** Web 버전 카카오 로그인 **/
    public String kakaoLogin(String code) {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code, redirectUri);

        // 2. 토큰으로 카카오 API 호출
        HashMap<String, Object> userInfo= getKakaoUserInfo(accessToken);

        //3. 카카오ID로 회원가입 & 로그인 처리
       User user= kakaoUserLogin(userInfo);

        return "성공";
    }





    //1. "인가 코드"로 "액세스 토큰" 요청
    private String getAccessToken(String code, String redirectUri) {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode.get("access_token").asText(); //토큰 전송
    }





    //2. 토큰으로 카카오 API 호출
    private HashMap<String, Object> getKakaoUserInfo(String accessToken) {
        HashMap<String, Object> userInfo= new HashMap<String,Object>();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();

        userInfo.put("id",id);
        userInfo.put("email",email);
        userInfo.put("nickname",nickname);

        return userInfo;
    }


    //3. 카카오ID로 회원가입 & 로그인 처리
    private User kakaoUserLogin(Map<String, Object> attributeMap){

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
       return   userRepository.save(user);
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