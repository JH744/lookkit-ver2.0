package synerjs.lookkit2nd.oauth2.token;

import synerjs.lookkit2nd.oauth2.MemberDTO;

public interface FetchTokenUseCase {
    Boolean validateToken(String accessToken);

    String getTokenFromKakao(String code);

    MemberDTO findUserByAccessToken(String accessToken);
}
