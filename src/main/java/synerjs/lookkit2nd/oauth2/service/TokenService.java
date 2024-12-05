package synerjs.lookkit2nd.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synerjs.lookkit2nd.oauth2.MemberDTO;
import synerjs.lookkit2nd.oauth2.token.CreateTokenUseCase;
import synerjs.lookkit2nd.oauth2.token.FetchTokenUseCase;
import synerjs.lookkit2nd.oauth2.token.UpdateTokenUseCase;
import synerjs.lookkit2nd.oauth2.token.response.TokenResponse;

@Service
@RequiredArgsConstructor
public class TokenService implements FetchTokenUseCase, CreateTokenUseCase, UpdateTokenUseCase {


    @Override
    public TokenResponse createToken(String userId) {
        return null;
    }

    @Override
    public Boolean validateToken(String accessToken) {
        return null;
    }

    @Override
    public String getTokenFromKakao(String code) {
        return "";
    }

    @Override
    public MemberDTO findUserByAccessToken(String accessToken) {
        return null;
    }
}
