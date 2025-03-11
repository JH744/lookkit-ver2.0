package synerjs.lookkit2nd.mypage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.user.User;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MyPageApiController {
    @Autowired
     PasswordEncoder encoder;

    @Autowired
    MypageService mypageService;

    // 비밀번호 검증 모달창
    @PostMapping("/mypage/verifyPassword")
    public ResponseEntity<Map<String, Object>> verifyPassword(@RequestBody PwVerifyDTO pwverifydto,
                                                              @RequestParam Long id){
        log.info("비밀번호 확인 요청 - Id {}, 입력된 비밀번호: {}", id, pwverifydto.getCurrentPassword());

        boolean isCorrect = mypageService.verifyPassword(id, pwverifydto.getCurrentPassword());
        Map<String, Object> response = new HashMap<>();

        if (isCorrect){
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "비밀번호가 올바르지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //  조회하기
    @GetMapping("/userinfo/{id}")
    public User show(@PathVariable Long id){
        return mypageService.show(id);
    }

    //  회원 정보 업데이트
    @PutMapping("/userinfo/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody MypageDTO dto) {
        // 1. 로그 확인
        log.info("id: {}, user: {}", id, dto.toString());
        // 2. 업데이트
        User updatedUser = mypageService.updateUser(id, dto);
        // 3. 오류체크
        if (updatedUser == null) {
            log.info("사용자 정보 수정 실패 - id: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 최종저장
        return ResponseEntity.ok(updatedUser);
    }

    //  삭제하기
    @DeleteMapping("/userinfo/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        User deleted = mypageService.delete(id);
        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //  비밀번호 변경
    @PostMapping("/userinfo/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody PwChangeDTO pwchangedto){

        // 1. 로그 확인
        log.info("비밀번호 변경 요청 - ID {}, 현재 비밀번호: {}, 새로운 비밀번호: {}",
                id, pwchangedto.getCurrentPassword(), pwchangedto.getNewPassword());
        // 2. 현재 비밀번호를 사용 중인지 확인
        if (mypageService.isCurrentPassword(id, pwchangedto.getNewPassword())){
            return ResponseEntity.badRequest().body("이미 사용 중인 비밀번호 입니다.");
        }

        System.out.println("newPassword"+pwchangedto.getNewPassword());
        System.out.println("newPassword"+pwchangedto.getConfirmNewPassword());

        // 3. 새로운 비밀번호가 두 번 입력된 것과 일치하는지 확인
        if (!pwchangedto.getNewPassword().equals(pwchangedto.getConfirmNewPassword())){
            return ResponseEntity.badRequest().body("새로운 비밀번호가 일치하지 않습니다.");
        }

        boolean result =encoder.matches(pwchangedto.getCurrentPassword(), mypageService.getCurrentPassword(id));

        if(!result){
            return ResponseEntity.badRequest().body("현재 비밀번호가 정확하지 않거나 회원 정보를 찾을 수 없습니다.");
        }
        // 4. 비밀번호 변경 시도
        boolean isUpdated = mypageService.updatePassword(id, pwchangedto.getNewPassword());
        return isUpdated ? ResponseEntity.ok("성공적으로 변경되었습니다.")
                : ResponseEntity.badRequest().body("비밀번호 변경 실패");
    }

    // 이메일 중복 확인 엔드포인트 추가
    @GetMapping("/userinfo/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailDuplicate(@RequestParam String email){
        log.info("이메일 중복 확인 요청 - 이메일: {}", email);
        boolean exists = mypageService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    // 이메일 변경
    @PatchMapping("/userinfo/{id}/change-email")
    public ResponseEntity<String> changeEmail(@PathVariable Long id, @RequestBody NewEmailDTO newEmail) {
        log.info("이메일 변경 요청 - ID {}, 새로운 이메일: {}", id, newEmail.getEmail());
        User updateUser = mypageService.updateEmail(id, newEmail.getEmail());
        return updateUser != null?
                ResponseEntity.ok("이메일이 성공적으로 수정되었습니다."):
                ResponseEntity.badRequest().body("중복된 이메일 이거나 회원 정보를 찾을 수 없습니다.");
    }
}
