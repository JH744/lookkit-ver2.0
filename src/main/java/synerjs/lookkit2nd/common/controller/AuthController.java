package synerjs.lookkit2nd.common.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.user.CustomUser;
import synerjs.lookkit2nd.user.UserDTO;
import synerjs.lookkit2nd.user.UserService;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;

    /*
     * 로그인
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data, HttpServletResponse response) {
        // 아이디와 비밀번호 가져오기
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        // 인증 수행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 유저네임 추출
        CustomUser customUser =(CustomUser)authentication.getPrincipal();
        String username = customUser.getUsername();

        // 유저 ROLE 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        // JWT 생성
        String jwt = jwtUtil.createJwt(username, role, 60*60*60L);

        // JWT를 쿠키에 저장
        var cookie = new Cookie("Authorization", jwt);
        cookie.setMaxAge(60 * 60 * 24); // 하루 유효
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 사용자 정보 가져오기(CustomUser 사용)
        // 반환 데이터
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Authorization", jwt); // JWT
        responseBody.put("userId", customUser.getUserId());     // 사용자 ID
        responseBody.put("username", customUser.getUsername()); // 사용자 이름
        responseBody.put("roles", customUser.getAuthorities()); // 권한 정보
        return ResponseEntity.ok(responseBody);
    }


    /**
     * 회원가입
     */
    @PostMapping("api/auth/signup")
    public ResponseEntity signUp(@RequestBody UserDTO user) {
        log.info("회원가입 요청 : {}",user);
        boolean result = userService.insertUser(user);
        log.info("회원가입 결과 : {}",result);
        if (result) {
            return ResponseEntity.status(200).body("회원가입 성공");
        } else {
            return ResponseEntity.status(500).body("회원가입 실패");
        }
    }


    /**
     * 로그아웃
     */
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        // JWT 쿠키를 삭제하기 위한 쿠키 설정
        log.info("로그아웃 진행");
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setDomain("localhost"); // 기존 쿠키의 Domain 확인 (필요 시 설정)
        cookie.setSecure(false); // HTTPS 환경이 아니라면 false로 설정
        response.addCookie(cookie);
        // 반환 데이터 (로그아웃 성공 메시지)
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Logout successful");
        return ResponseEntity.ok(responseBody);
    }

}
