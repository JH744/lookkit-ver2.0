package synerjs.lookkit2nd.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import synerjs.lookkit2nd.user.CustomUser;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

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
                .claim("userID", user.getUserId())
                .claim("authorities", user.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key) //해싱 키 삽입
                .compact();
        return jwt;
    }

    // JWT 파싱 매서드
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }

}