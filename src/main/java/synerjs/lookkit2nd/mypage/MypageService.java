package synerjs.lookkit2nd.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.user.User;
import synerjs.lookkit2nd.user.UserRepository;

import java.util.List;

@Service
public class MypageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;



    public List<User> index() {
        return userRepository.findAll();
    }

    public User show(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, MypageDTO dto) {
        // 1. 대상 사용자 조회
        User target = userRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }

        // 2. DTO에서 전달된 정보로 대상 사용자 업데이트
        target.setUserName(dto.getUserName());
        target.setPhone(dto.getPhone());
        target.setBirthDate(dto.getBirthDate());
        target.setEmail(dto.getEmail());
        target.setAddress(dto.getAddress());
        target.setLastUpdate(new java.sql.Timestamp(System.currentTimeMillis())); // 마지막 업데이트 시간 설정

        return userRepository.save(target);
    }

    public User delete(Long id) {
        // 1. 대상 찾기
        User target = userRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null){
            return null;
        }
        // 3. 대상 삭제하기
        userRepository.delete(target);
        return target;
    }

    public User updateEmail(Long id, String newEmail) {
        // 1. 새 이메일이 이미 존재하는지 확인
        if (userRepository.existsByEmail(newEmail)){
            return null; // 중복된 이메일일 경우 null 반환 또는 에러 처리
        }
        // 2. 사용자 ID로 대상 찾기
        User target = userRepository.findById(id).orElse(null);
        if (target == null){
            return null;
        }
        // 3. 이메일 업데이트 후 저장
        target.setEmail(newEmail);
        target.setLastUpdate(new java.sql.Timestamp(System.currentTimeMillis())); // 마지막 업데이트 시간 설정
        return userRepository.save(target);
    }
        //  현재 비밀번호 확인
    public boolean isCurrentPassword(Long id, String newPassword){
        User user = userRepository.findById(id).orElse(null);
        return user != null && user.getPassword().equals(newPassword); // 현재 비밀번호가 새 비밀번호와 같은지 확인
    }

    public String getCurrentPassword(Long id){
        User user = userRepository.findById(id).orElse(null);
        return user.getPassword(); //
    }


        // 비밀번호 변경
    public boolean updatePassword(Long id, String newPassword){
        User user = userRepository.findById(id).orElse(null);
        user.setPassword(encoder.encode(newPassword));// 암호화
        // 새로운 비밀번호로 업데이트
        user.setLastUpdate(new java.sql.Timestamp(System.currentTimeMillis())); // 마지막 업데이트 시간 설정
        userRepository.save(user);
        return true;
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean verifyPassword(Long id, String currentPassword) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return false; // 사용자 정보가 없는 경우
        }
        // 암호화된 비밀번호와 비교
        return encoder.matches(currentPassword, user.getPassword());
    }
}
