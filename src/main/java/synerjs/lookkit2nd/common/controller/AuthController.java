package synerjs.lookkit2nd.common.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.util.JwtUtil;
import synerjs.lookkit2nd.user.CustomUser;
import synerjs.lookkit2nd.user.UserDTO;
import synerjs.lookkit2nd.user.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



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
    public ResponseEntity signUp(@RequestBody UserDTO user){
        System.out.println("응답받음");
        System.out.println(user.getUserName());
        System.out.println(user.getUserUuid());
        System.out.println(user.getEmail());
        System.out.println(user.getGender());
        System.out.println(user.getAddress());
     boolean result  = userService.insertUser(user);
        System.out.println("회원가입결과:"+result);
     if (result){
       return    ResponseEntity.status(200).body("회원가입 성공");
     }else {
        return ResponseEntity.status(500).body("회원가입 실패");
     }
    }

//    @GetMapping("/api/auth/test")
//    public ResponseEntity test(Authentication auth){
//        System.out.println("auth test 응답받음");
//        CustomUser user=(CustomUser)auth.getPrincipal();
//        System.out.println("auth"+auth);
//        System.out.println("user"+user);
//        System.out.println("저장된 유저 유저id : "+user.getUserId());
//        System.out.println("유저uuid : "+auth.getName());
//        System.out.println("role : "+auth.getAuthorities());
//        System.out.println("인증여부: "+auth.isAuthenticated());
//
//        if (true){
//            return    ResponseEntity.status(200).body("테스트 성공");
//        }else {
//            return ResponseEntity.status(500).body("테스트 실패");
//        }
//    }


    @GetMapping("/kakao-login")
    public String kakaoLogin(@RequestParam String code) {
        return "hello kakao, code=" + code;
    }

}
