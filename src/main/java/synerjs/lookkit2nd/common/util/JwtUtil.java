package synerjs.lookkit2nd.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import synerjs.lookkit2nd.user.CustomUser;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        if (token == null || token.isEmpty()) {
            return true;
        }
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
            .claim("username", username)
            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredMs))
            .signWith(secretKey)
            .compact();
    }

    //밑에는 일반로그인 jwt 방식구현을 위한 로직. 현재 분리되어있지만 합칠 예정
    // 해싱키 설정하기
    static final SecretKey key =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
        ));

    // JWT 생성 매서드
    public static String createToken(Authentication auth) {
        CustomUser user =(CustomUser)auth.getPrincipal();
        user.getAuthorities().stream().map(data->data.getAuthority()).collect(Collectors.joining(","));


        String jwt = Jwts.builder()
            .claim("username", user.getUsername())
            .claim("userId", user.getUserId())
            .claim("authorities", user.getAuthorities())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 60000))
            .signWith(key) //해싱 키 삽입
            .compact();
        return jwt;
    }

}