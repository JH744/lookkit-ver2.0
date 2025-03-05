package synerjs.lookkit2nd.oauth2.service;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("CustomOAuth2UserService 실행 >> {}", oAuth2User);


        // 공급자 확인 -> OAuth2Response 객체 생성
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> {
                log.info("네이버 로그인 요청");
                yield new NaverResponse(attributes);
            }
            case "google" -> {
                log.info("구글 로그인 요청");
                yield new GoogleResponse(attributes);
            }
            case "kakao" -> {
                log.info("카카오 로그인 요청");
                yield new KakaoResponse(attributes);
            }
            default -> null;
        };

        // DB조회
        log.info("검증할 아이디 : {}", oAuth2Response.getUserUuid());
        Optional<User> existData = userRepository.findByUserUuid(oAuth2Response.getUserUuid());

        // 기존 회원여부에 따라 회원가입 or 업데이트 진행
        if (existData.isEmpty()) {
            return signUpOAuth2User(oAuth2Response);
        }
        return updateOAuth2User(oAuth2Response, existData);
    }

    // 신규유저 - 회원가입
    private CustomOAuth2User signUpOAuth2User(OAuth2Response oAuth2Response) {
        log.info("회원가입 진행");
        User userEntity = User.builder()
            .userUuid(oAuth2Response.getUserUuid())
            .password(passwordEncoder.encode("password"))
            .email(oAuth2Response.getProvider() + "_" + oAuth2Response.getEmail())
            .phone(oAuth2Response.getPhone())
            .address("미제공")
            .userName(oAuth2Response.getName())
            .birthDate(oAuth2Response.getBirthDate())
            .gender(oAuth2Response.getGender())
            .build();
        User savedUserEntity = userRepository.save(userEntity);  // 유저엔티티 만들어서 저장
        UserDTO userDTO = userService.convertToDTO(savedUserEntity);  // 유저엔티티 -> dto 변환 후 반환
        return new CustomOAuth2User(userDTO);
    }

    // 기존 유저- 최신정보 동기화를 위해 업데이트 진행
    private CustomOAuth2User updateOAuth2User(OAuth2Response oAuth2Response,
        Optional<User> existData) {
        log.info("업데이트 진행");
        User userEntity = existData.get();
        userEntity.setEmail(oAuth2Response.getProvider() + "_"
            + oAuth2Response.getEmail()); //ex) kakao_user@naver.com
        userEntity.setUserName(oAuth2Response.getName());
        userRepository.save(userEntity);
        UserDTO userDTO = userService.convertToDTO(userEntity);
        return new CustomOAuth2User(userDTO);
    }

}