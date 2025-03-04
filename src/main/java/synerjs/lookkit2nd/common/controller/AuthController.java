package synerjs.lookkit2nd.common.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.oauth2.KakaoUserInfoResponseDto;
import synerjs.lookkit2nd.oauth2.service.KakaoService;
import synerjs.lookkit2nd.user.CustomUser;
import synerjs.lookkit2nd.user.UserDTO;
import synerjs.lookkit2nd.user.UserService;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final KakaoService kakaoService;


    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data, HttpServletResponse response) {
        // 아이디와 비밀번호 가져오기
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );

        // 인증 수행
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // JWT 생성
        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        // JWT를 쿠키에 저장
        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 60 * 24); // 하루 유효
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 사용자 정보 가져오기(CustomUser 사용)
        CustomUser user = (CustomUser) auth.getPrincipal();

        // 반환 데이터
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("jwt", jwt); // JWT
        responseBody.put("userId", user.getUserId());     // 사용자 ID
        responseBody.put("username", user.getUsername()); // 사용자 이름
        responseBody.put("roles", user.getAuthorities()); // 권한 정보

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("api/auth/signup")
    public ResponseEntity signUp(@RequestBody UserDTO user) {
        System.out.println("응답받음");
        System.out.println(user.getUserName());
        System.out.println(user.getUserUuid());
        System.out.println(user.getEmail());
        System.out.println(user.getGender());
        System.out.println(user.getAddress());
        boolean result = userService.insertUser(user);
        System.out.println("회원가입결과:" + result);
        if (result) {
            return ResponseEntity.status(200).body("회원가입 성공");
        } else {
            return ResponseEntity.status(500).body("회원가입 실패");
        }
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse response) {
        // JWT 쿠키를 삭제하기 위한 쿠키 설정
        System.out.println("로그아웃진행");
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setDomain("localhost"); // 기존 쿠키의 Domain 확인 (필요 시 설정)
        cookie.setSecure(false); // HTTPS 환경이 아니라면 false로 설정
        // cookie.setDomain("example.com"); // 쿠키의 도메인이 필요할 경우 설정
        // newCookie.setDomain("yourDomain.com"); // 도메인 설정할 경우 추가
        response.addCookie(cookie);
        // 반환 데이터 (로그아웃 성공 메시지)
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Logout successful");
        return ResponseEntity.ok(responseBody);
    }

}
