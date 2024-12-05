package synerjs.lookkit2nd.oauth2.token;

import synerjs.lookkit2nd.oauth2.token.response.TokenResponse;

public interface CreateTokenUseCase {
    TokenResponse createToken(String userId);
}
