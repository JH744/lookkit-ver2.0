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
import synerjs.lookkit2nd.common.provider.CookieProvider;
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
    private final CookieProvider cookieProvider;

    /*
     * 로그인
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data, HttpServletResponse response) {
        // 아이디와 비밀번호 가져오기
        UsernamePasswordAuthenticationToken authToken
            = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        // 인증 수행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUser customUser =(CustomUser)authentication.getPrincipal();

        // 유저네임 추출
        String username = customUser.getUsername();

        // 유저 ROLE 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // JWT 생성
        String jwt = jwtUtil.createJwt(username, role, 60*60*60L);

        // 쿠키생성 후 jwt 저장 -> 응답에 쿠키 반영
        response.addCookie(cookieProvider.createJwtCookie("Authorization",jwt));
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
    @PostMapping("/api/auth/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO user) {
        log.info("회원가입 요청 : {}",user);
        boolean result = userService.insertUser(user);
        log.info("회원가입 결과 : {}",result);
        if (result) {
            return ResponseEntity.status(200).body("회원가입 성공");
        }
            return ResponseEntity.status(500).body("회원가입 실패");

    }


    /**
     * 로그아웃
     */
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        log.info("로그아웃 진행");
        // JWT 쿠키 만료시키기
        response.addCookie(cookieProvider.expireJwtCookie());
        // 반환 데이터 (로그아웃 성공 메시지)
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "로그아웃 성공");
        return ResponseEntity.ok(responseBody);
    }


}
