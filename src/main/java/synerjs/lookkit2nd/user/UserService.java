package synerjs.lookkit2nd.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 유저를 찾지못함: " + userId));
    }

    // 아이디로 유저 조회
    public User findByUserUuid(String username) {
        return userRepository.findByUserUuid(username).orElse(null);
    }


    //회원가입 하기
    public boolean insertUser(UserDTO userDto) {
        boolean result = true;
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));// 암호화
             User user  = userDto.toEntity(userDto);  // dto -> 엔티티 변환
            userRepository.save(user);
        }catch (Exception e){
            System.out.println("에러발생: "+e.getMessage());
           result =false; // 에러 발생시 결과에 false 리턴
        }
        return result;
    }

    public User getUserInfo(Long id) {
        return userRepository.findById(id).get();
    }

    public String findById(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String email = userDTO.getEmail();

        return userRepository.findByUserNameAndEmail(userName, email)
                .map(User::getUserUuid)  // 값이 존재할 경우 User의 userUuid 반환하기
                .orElse("결과없음");
       }


       // 아이디 조회 후 비밀번호 수정하기
    public String updatePassword(UserDTO userDTO) {
     Optional<User> optionalUser =  userRepository.findByUserUuid(userDTO.getUserUuid());
        if(optionalUser.isPresent()){
           User user= optionalUser.get();
           user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            return "성공";
        }else {
            return "실패";
        }
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userUuid(user.getUserUuid())
                .userName(user.getUserName())
                .email(user.getEmail())
                .gender(user.getGender())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .role("USER")
                .build();
    }

}
