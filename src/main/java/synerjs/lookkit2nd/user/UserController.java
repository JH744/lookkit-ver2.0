package synerjs.lookkit2nd.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/check-id")
    public ResponseEntity checkIdDuplication(@RequestParam  String userUuid){
       User result =  userService.findByUserUuid(userUuid);
        if (result == null){
            return    ResponseEntity.status(200).body("사용 가능한 아이디");
        }else {
            return ResponseEntity.status(500).body("중복 아이디");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id){
        User user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }



    @PostMapping("/find/id")
    public ResponseEntity findUserId(@RequestBody UserDTO userDTO){

        String result =  userService.findById(userDTO);
        if (result.equals("결과없음")){
            return ResponseEntity.status(500).body("일치하는 유저아이디가 없습니다.");
        }else {
            return  ResponseEntity.status(200).body(result);
        }

    }


    @PostMapping("/update/password")
    public ResponseEntity updatePassword(@RequestBody UserDTO userDTO){
        String result = userService.updatePassword(userDTO);
        if (result.equals("실패")){
            return ResponseEntity.status(500).body("비밀번호 변경에 실패했습니다.");
        }else {
            return  ResponseEntity.status(200).body(result);
        }
    }






}