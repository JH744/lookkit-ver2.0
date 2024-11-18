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

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    @PostMapping("/api/auth/login")
    public String login(@RequestBody  Map<String,String> data, HttpServletResponse response,Authentication authvariable){
        // body로부터 아이디,비밀번호 가져오기
        // 사용자 인증을 위한 UsernamePasswordAuthenticationToken 생성
         var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        // AuthenticationManagerBuilder를 사용하여 인증 수행
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        // SecurityContextHolder에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(auth);

       String jwt=JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("jwt : "+jwt);

        // 쿠키 생성 + jwt 보관
        var cookie = new Cookie("jwt", jwt); //Key:value로 저장가능
        cookie.setMaxAge(60 * 60 * 24);         // 하루 동안 유효
        cookie.setHttpOnly(true);             // HttpOnly 설정으로 자바스크립트 접근 방지
        cookie.setPath("/");                  // 쿠키가 전송될 URL 경로를 모든 경로로 설정
        response.addCookie(cookie);           // 브라우저에 쿠키 전달



        // auth 확인
        if (authvariable != null) {
            System.out.println("auth변수확인");
       CustomUser user =(CustomUser)authvariable.getPrincipal();
            System.out.println(user);
            System.out.println(user.getUserId());
            System.out.println(user.getAuthorities());
            System.out.println(user.getUsername());
        }

        return jwt;
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



    @GetMapping("/kakao-login")
    public String kakaoLogin(@RequestParam String code) {
        return "hello kakao, code=" + code;
    }

}
