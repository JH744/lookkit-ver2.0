package synerjs.lookkit2nd.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //아이디 중복 체크
    @GetMapping("/check-id")
    public ResponseEntity<String> checkIdDuplication(@RequestParam String userUuid) {
        boolean isDuplicate = Optional.ofNullable(userService.findByUserUuid(userUuid)).isPresent();
        if (isDuplicate) {
            return ResponseEntity.status(409).body("중복 아이디");
        }
        return ResponseEntity.status(200).body("사용 가능한 아이디");
    }


    // 유저정보 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }


    // 아이디로 유저 찾기
    @PostMapping("/find/id")
    public ResponseEntity<String> findUserId(@RequestBody UserDTO userDTO) {
        String result = userService.findById(userDTO);

        if ("결과없음".equals(result)) {
            return ResponseEntity.status(500).body("일치하는 유저아이디가 없습니다.");
        }

        return ResponseEntity.status(200).body(result);

    }

    // 비밀번호 재설정
    @PostMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserDTO userDTO) {
        String result = userService.updatePassword(userDTO);
        if ("실패".equals(result)) {
            return ResponseEntity.status(500).body("비밀번호 변경에 실패했습니다.");
        }
        return ResponseEntity.status(200).body(result);
    }


}