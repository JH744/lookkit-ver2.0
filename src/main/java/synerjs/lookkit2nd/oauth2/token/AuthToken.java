package synerjs.lookkit2nd.oauth2.token;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthToken {
    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime accessTokenExpireAt;
    private final LocalDateTime refreshTokenExpireAt;

    public AuthToken(String accessToken, String refreshToken, LocalDateTime accessTokenExpireAt, LocalDateTime refreshTokenExpireAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireAt = accessTokenExpireAt;
        this.refreshTokenExpireAt = refreshTokenExpireAt;
    }
}
