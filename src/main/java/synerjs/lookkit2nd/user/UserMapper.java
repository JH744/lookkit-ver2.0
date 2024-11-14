package synerjs.lookkit2nd.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto){
             User user = User.builder()
                .userUuid(dto.getUserUuid())
                .userName(dto.getUserName())
                .gender(dto.getGender())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .birthDate(dto.getBirthDate())
                .build();
        return user;
    }
}
