package synerjs.lookkit2nd.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.oauth2.OAuth2Attributes;
import synerjs.lookkit2nd.user.UserDTO;
import synerjs.lookkit2nd.user.UserService;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService; // DB 사용자 관리 서비스

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService 실행>>>>>");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // provider 정보: kakao인지 google인지
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // user-name-attribute 설정 (카카오의 경우 id)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 카카오 사용자 정보를 바탕으로 UserDTO나 엔티티 생성/조회
        OAuth2Attributes attributes = OAuth2Attributes.ofKakao(oAuth2User.getAttributes());
        // OAuth2Attributes는 카카오 JSON 응답을 파싱하는 클래스이며, 사용자 정보 추출 로직 구현 필요


        // DB에 사용자 정보 저장 또는 조회
        UserDTO userDto = userService.findOrCreateKakaoUser(attributes);


        // Spring Security의 OAuth2User 구현체 반환
        Map<String, Object> userAttributes = attributes.toMap();
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttributes,
                userNameAttributeName
        );
    }
}