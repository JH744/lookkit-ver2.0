//package synerjs.lookkit2nd.common.util;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import synerjs.lookkit2nd.user.User;
//import synerjs.lookkit2nd.user.UserRepository;
//
//import java.util.Map;
//
//@RequiredArgsConstructor
//public class TokenUtil {
//    private  final UserRepository userRepository;
//
//    private String getTokenFromKakao(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "your-client-id"); // 카카오 REST API 키
//        params.add("redirect_uri", "http://localhost:8080/api/v1/auth/callback");
//        params.add("code", code);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(
//                "https://kauth.kakao.com/oauth/token",
//                request,
//                Map.class
//        );
//
//        Map<String, Object> responseBody = response.getBody();
//        return (String) responseBody.get("access_token");
//    }
//
//
//    private Map<String, Object> getUserInfoFromKakao(String accessToken) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken); // Authorization 헤더에 Bearer 추가
//
//        HttpEntity<Void> request = new HttpEntity<>(headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.GET,
//                request,
//                Map.class
//        );
//
//        return response.getBody();
//    }
//
//
//
//    private User processUser(Map<String, Object> userInfo) {
//        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
//        String email = (String) kakaoAccount.get("email");
//        String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
//        String gender = (String) kakaoAccount.get("gender");
//
//            gender=  gender.equals("female") ? "F" : "M";
//
//        // 데이터베이스에서 이메일로 사용자 검색
//        return userRepository.findByEmail(email)
//                .orElseGet(() -> userRepository.save(
//                        User.builder()
//                                .email(email)
//                                .userName(nickname)
//                                .gender(gender)
//                                .role("USER")
//                                .build()
//                ));
//    }
//
//
//
//
//
//
//}
