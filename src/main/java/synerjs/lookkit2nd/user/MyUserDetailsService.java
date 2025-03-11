package synerjs.lookkit2nd.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 유저 조회
        User user = userService.findByUserUuid(username);


        // 유저 조회 불가 시 인증실패 메시지 표시
        if (user == null) {
            throw new UsernameNotFoundException("해당 아이디를 가진 사용자가 없습니다: " + username);
        }

        // CustomerUser 객체 안에 권한 추가
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        String role = user.getRole();
        String authority = "ADMIN".equals(role)
            ? "ROLE_ADMIN"
            : "ROLE_USER";

        log.info("{} 권한계정으로 확인됩니다.",authority);

        userAuthorities.add(new SimpleGrantedAuthority(authority));

        // 유저아이디, 유저비밀번호, 유저권한, 유저PK-ID를 세션에 저장
        return new CustomUser(user.getUserUuid(), user.getPassword(), userAuthorities,
            user.getUserId());
    }
}




