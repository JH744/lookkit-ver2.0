package synerjs.lookkit2nd.user;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// @RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    // 아이디로 유저 조회
    public User findByUserUuid(String username){
       Optional<User> optionalUser  =userRepository.findByUserUuid(username);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }else {
            return null;
        }
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
}
