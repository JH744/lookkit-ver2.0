package synerjs.lookkit2nd.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.oauth2.dto.CustomOAuth2User;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${DOMAIN_URI}")
    private String domainUri;

    // 인증 성공시 jwt토큰을 쿠키로 발급함
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("successHandler 동작");

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
       //System.out.println("onAuthenticationSuccess : customOAuth2User: " + customUserDetails);

        // 유저 아이디 추출
        String username = customUserDetails.getName();


        // 유저 ROLE 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        // JWT 토큰 생성
        String jwtToken = jwtUtil.createJwt(username, role, 60*60*60L);
        log.info("생성된 jwt >>>>>>>>>>>>>> : {} ", jwtToken);


        // 쿠키에 JWT토큰 저장 후 응답
        response.addCookie(createCookie("Authorization", jwtToken));
        response.sendRedirect(domainUri); // 토큰 쿠키발급 후 메인페이지 redirect
    }

    // 쿠키 생성
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}