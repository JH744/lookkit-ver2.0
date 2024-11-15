package synerjs.lookkit2nd.mypage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.user.User;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MyPageApiController {
    @Autowired
    MypageService mypageService;

    //  조회하기
    @GetMapping("/userinfo/{id}")
    public User show(@PathVariable Long id){
        return mypageService.show(id);
    }

    //  회원 정보 업데이트
    @PatchMapping("/userinfo/{id}")
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
    @PatchMapping("/userinfo/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody PwChangeDTO pwchangedto){
        // 1. 로그 확인
        log.info("비밀번호 변경 요청 - ID {}, 현재 비밀번호: {}, 새로운 비밀번호: {}",
                id, pwchangedto.getCurrentPassword(), pwchangedto.getNewPassword());
        // 2. 현재 비밀번호를 사용 중인지 확인
        if (mypageService.isCurrentPassword(id, pwchangedto.getNewPassword())){
            return ResponseEntity.badRequest().body("이미 사용 중인 비밀번호 입니다.");
        }
        // 3. 새로운 비밀번호가 두 번 입력된 것과 일치하는지 확인
        if (!pwchangedto.getNewPassword().equals(pwchangedto.getConfirmNewPassword())){
            return ResponseEntity.badRequest().body("새로운 비밀번호가 일치하지 않습니다.");
        }
        // 4. 비밀번호 변경 시도
        boolean isUpdated = mypageService.updatePassword(id, pwchangedto.getCurrentPassword(), pwchangedto.getNewPassword());
        return isUpdated ? ResponseEntity.ok("성공적으로 변경되었습니다.")
                : ResponseEntity.badRequest().body("현재 비밀번호가 정확하지 않거나 회원 정보를 찾을 수 없습니다.");
//        if (!isUpdated){
//            return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않거나 사용자 정보가 없습니다.");
//        }
//        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
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
//        if (updateUser == null) {
//            log.info("중복된 이메일 이거나 사용자 없음");
//            return ResponseEntity.badRequest().body("이메일 중복 또는 사용자 정보 없음");
//
//        return ResponseEntity.ok("이메일이 성공적으로 변경되었습니다.");
