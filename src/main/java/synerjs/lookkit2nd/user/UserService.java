package synerjs.lookkit2nd.user;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.oauth2.KakaoUserInfoResponseDto;
import synerjs.lookkit2nd.oauth2.OAuth2Attributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public UserDTO findOrCreateKakaoUser(KakaoUserInfoResponseDto userInfo) {
        String userUuid = "kakao_" + userInfo.getId();

        // 1. 기존 사용자 조회
        Optional<User> existingUserOpt = userRepository.findByUserUuid(userUuid);
        if (existingUserOpt.isPresent()) {
            return convertToDTO(existingUserOpt.get());
        }else {

            // 2. 신규 사용자 생성
            // 여기서 KakaoUserInfoResponseDto의 toEntity 활용
            User newUser = userInfo.toEntity(userInfo);

            // userUuid 필드를 별도로 관리하고 싶다면, toEntity 내부 로직을 수정하거나 아래와 같이 직접 설정
            // newUser.setUserUuid(userUuid);

            User savedUser = userRepository.save(newUser);
            return convertToDTO(savedUser);
        }
    }

    public UserDTO findOrCreateKakaoUser(OAuth2Attributes attributes) {
        // attributes에서 필요한 필드 추출
        String email = attributes.getEmail();
        String userUuid = attributes.getUserUuid();
        String userName = attributes.getUserName();
        String gender = attributes.getGender();
        LocalDate birthDate = attributes.getBirthDate();
        String phone = attributes.getPhone();
        String address = attributes.getAddress();

        // 기존 사용자 조회
        Optional<User> existingUserOpt = userRepository.findByEmail(email);

        if (existingUserOpt.isPresent()) {
            // 기존 사용자 존재 시, UserDTO로 변환하여 반환
            User existingUser = existingUserOpt.get();
            return convertToDTO(existingUser);
        }

        // 신규 사용자 생성
        User newUser = User.builder()
                .userUuid(userUuid)
                .userName(userName)
                .email(email)
                .gender(gender)
                .birthDate(birthDate)
                .phone(phone)
                .address(address)
                .role("USER") // 기본 사용자 역할
                .password("SOCIAL_LOGIN_USER") // 더미 비밀번호
                .build();

        // 사용자 저장
        User savedUser = userRepository.save(newUser);

        // 저장된 사용자 정보를 DTO로 변환하여 반환
        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
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
