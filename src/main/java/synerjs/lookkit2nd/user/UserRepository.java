package synerjs.lookkit2nd.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synerjs.lookkit2nd.mypage.NewEmailDTO;

import java.util.ArrayList;
import java.util.Optional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email); // 이메일 중복 확인
    Optional<User> findByEmail(String email); // 이메일 통해 사용자 조

    Optional<User> findByUserUuid(String username);
}
