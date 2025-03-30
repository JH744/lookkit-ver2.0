package synerjs.lookkit2nd.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        try {
            // 패스워드 암호화
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            // DTO -> 엔티티 변환
            User user  = userDto.toEntity(userDto);
            userRepository.save(user);
            log.info("회원가입 완료 : {}",user.getUserName());
            return true;
        }catch (Exception e){
            log.error("회원가입 중 에러발생 : {}",e.getMessage());
            return false;
        }
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


       // 아이디 조회 후 비밀번호 수정하기 삭제예정
//    public String updatePassword(UserDTO userDTO) {
//     Optional<User> optionalUser =  userRepository.findByUserUuid(userDTO.getUserUuid());
//        if(optionalUser.isPresent()){
//           User user= optionalUser.get();
//           user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userRepository.save(user);
//            return "성공";
//        }else {
//            return "실패";
//        }
//    }

    // 아이디 조회 후 비밀번호 수정하기
    public String updatePassword(UserDTO userDTO) {
        return userRepository.findByUserUuid(userDTO.getUserUuid())
            .map(user -> {
                String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return "성공";
            })
            .orElse("실패");
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
