package synerjs.lookkit2nd.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    @GetMapping("/api/auth/login")
    public String login(@RequestBody  Map<String,String> data){
        //로그인 ajax-post요청. body로부터 아이디,비밀번호 가져옴

        // 사용자 인증을 위한 UsernamePasswordAuthenticationToken 생성
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        // AuthenticationManagerBuilder를 사용하여 인증 수행
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        // SecurityContextHolder에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "";

    }

    @GetMapping("/kakao-login")
    public String kakaoLogin(@RequestParam String code) {
        return "hello kakao, code=" + code;
    }

}
