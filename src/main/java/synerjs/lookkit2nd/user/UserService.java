package synerjs.lookkit2nd.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


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
}
