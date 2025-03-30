package synerjs.lookkit2nd.user;

import java.util.Optional;
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
        User user = Optional.ofNullable(userService.findByUserUuid(username))
            .orElseThrow(() -> {
                log.warn("사용자 조회 실패 >>>>>> {}", username);
                return new UsernameNotFoundException("해당 아이디를 가진 사용자가 없습니다: " + username);
            });

        // CustomerUser 객체 안에 권한 추가
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(new SimpleGrantedAuthority(getAuthority(user)));

        // 유저아이디, 유저비밀번호, 유저권한, 유저PK-ID를 세션에 저장
        return new CustomUser(user.getUserUuid(), user.getPassword(), userAuthorities,
            user.getUserId());
    }


    private static String getAuthority(User user) {
        String authority = "ADMIN".equals(user.getRole())
            ? "ROLE_ADMIN"
            : "ROLE_USER";
        log.info("{} 권한계정으로 확인됩니다.", authority);
        return authority;
    }
}




