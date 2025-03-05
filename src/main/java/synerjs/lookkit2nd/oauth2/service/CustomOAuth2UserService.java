package synerjs.lookkit2nd.oauth2.service;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.oauth2.dto.CustomOAuth2User;
import synerjs.lookkit2nd.oauth2.dto.GoogleResponse;
import synerjs.lookkit2nd.oauth2.dto.KakaoResponse;
import synerjs.lookkit2nd.oauth2.dto.NaverResponse;
import synerjs.lookkit2nd.oauth2.dto.OAuth2Response;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserDTO;
import synerjs.lookkit2nd.user.UserRepository;
import synerjs.lookkit2nd.user.UserService;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("CustomOAuth2UserService 실행>>>>>"+oAuth2User);


        // provider 확인 -> dto객체 생성
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes =oAuth2User.getAttributes();

        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> {
                System.out.println("네이버 로그인 요청");
                yield new NaverResponse(attributes);
            }
            case "google" -> {
                System.out.println("구글 로그인 요청");
                yield new GoogleResponse(attributes);
            }
            case "kakao" -> {
                System.out.println("카카오 로그인 요청");
                yield new KakaoResponse(attributes);
            }
            default -> null;
        };

        System.out.println("검증할 아이디 : "+oAuth2Response.getUserUuid());
        //DB조회
        Optional<User> existData = userRepository.findByUserUuid(oAuth2Response.getUserUuid());

        // db에서 가져온 회원이 없다면 회원가입 진행
        if (existData.isEmpty()) {
            System.out.println("회원가입 진행");
            User userEntity =  User.builder()
                .userUuid(oAuth2Response.getUserUuid())
                .password(passwordEncoder.encode("password"))
                .email(oAuth2Response.getProvider()+"_"+oAuth2Response.getEmail())
                .phone(oAuth2Response.getPhone())
                .address("미제공")
                .userName(oAuth2Response.getName())
                .birthDate(oAuth2Response.getBirthDate())
                .gender(oAuth2Response.getGender())
                .build();
               User savedUserEntity =userRepository.save(userEntity);  // 유저엔티티 만들어서 저장
             UserDTO userDTO=userService.convertToDTO(savedUserEntity);  // 유저엔티티 -> dto 변환 후 반환
            return new CustomOAuth2User(userDTO);
        }
        // 존재한다면 이메일, 이름을 재설정 후 업데이트 진행
        else {
            System.out.println("업데이트만 진행");
           User userEntity= existData.get();
            userEntity.setEmail(oAuth2Response.getProvider()+"_"+oAuth2Response.getEmail());
            userEntity.setUserName(oAuth2Response.getName());
            // 업데이트 진행
            userRepository.save(userEntity);
            UserDTO userDTO=userService.convertToDTO(userEntity);  // 유저엔티티 -> dto 변환 후 반환
            return new CustomOAuth2User(userDTO);
        }
    }
}