package synerjs.lookkit2nd.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.oauth2.dto.CustomOAuth2User;
import synerjs.lookkit2nd.user.UserDTO;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    // 생성자
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        // 이름이 'Authorization'인 쿠키를 찾아 반환
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if ("Authorization".equals(cookie.getName())) {
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        // Authorization 쿠키가 없으면 바로 필터체인 진행
        if (authorization == null) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }


//Authorization 헤더 검증
        if(authorization == null) {
            System.out.println("token null");
            // 필터체인 다시 타도록
            filterChain.doFilter(request, response);
            return;
        }

//토큰
        String token = authorization;


//토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
//조건이 해당되면 메소드 종료 (필수)
            return;
        }

//토큰 검증해 유효하다면 => 토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

//userDTO를 생성하여 값set
        UserDTO userDTO = UserDTO.builder()
            .userUuid(username) // username = 아이디값을 가지고 있음
            .role(role)
            .build();

//UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

//스프링 시큐리티 인증 토큰 생성 / Authenctication 객체 생성
        Authentication authToken =
            new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
//세션에 사용자 등록.  // contextHolder에 Authenctication 추가
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}