package synerjs.lookkit2nd.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class KakaoLoginDto {

    public String accessToken;
    public String refreshToken;
}