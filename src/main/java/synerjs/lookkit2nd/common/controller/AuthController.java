package synerjs.lookkit2nd.common.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {


    @GetMapping("/kakao-login")
    public String kakaoLogin(@RequestParam String code) {
        return "hello kakao, code=" + code;
    }

}
