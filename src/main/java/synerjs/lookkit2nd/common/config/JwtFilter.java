package synerjs.lookkit2nd.common.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.user.CustomUser;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 정적리소스 필터 적용 제외 설정
        String path = request.getRequestURI();

        // 특정 경로 제외
        if (path.startsWith("/assets") || path.equals("/favicon.ico") || path.startsWith("/public")) {
            filterChain.doFilter(request, response); // 다음 필터로 넘어감
            return;
        }
        System.out.println("jwt필터실행");
        // 쿠기 가져오기
        Cookie[] cookies = request.getCookies(); // 담겨있는 쿠키가 배열형태로 반환

        if(cookies == null){ // 쿠키 null이면 필터 통과
            filterChain.doFilter(request,response);
            return;
        }
        var jwtCookie = "";
        for (int i = 0; i < cookies.length; i++){ //쿠키들 속 이름이 'jwt'인 쿠키를 변수에 저장
            if (cookies[i].getName().equals("jwt")){
                jwtCookie = cookies[i].getValue();
            }
        }
        System.out.println(jwtCookie);

        // 유효성 검증,위조 여부 확인
        Claims claims;
        try {
        //
        claims =JwtUtil.extractToken(jwtCookie); //토큰 파싱

        }catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }
        var arr =  claims.get("authorities").toString().split(",");
        String username = claims.get("username").toString(); //토큰에서 유저아이디 가져오기
        long userId = Double.valueOf(claims.get("userID").toString()).longValue(); // 토큰에서 유저 PK ID값 가져오기

         var authorities= Arrays.stream(arr).map(a-> new SimpleGrantedAuthority(a)).toList();

        var customUser = new CustomUser(username,"none",authorities,userId);
        var authToken = new UsernamePasswordAuthenticationToken(
                customUser,""
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }
}
