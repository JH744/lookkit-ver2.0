package synerjs.lookkit2nd.common.provider;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    // jwt쿠키 생성하기
    public Cookie createJwtCookie(String key, String jwt) {
        var cookie = new Cookie(key, jwt);
        cookie.setMaxAge(60 * 60 * 24); // 하루 유효
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }


     public Cookie expireJwtCookie() {
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setDomain("localhost"); // 기존 쿠키의 Domain 확인 (필요 시 설정)
        cookie.setSecure(false); // HTTPS 환경이 아니라면 false로 설정
        return cookie;
    }

}
