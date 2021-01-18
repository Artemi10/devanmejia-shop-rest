package devanmejia.services.tokens;

import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface TokensService {
    Tokens updateTokensByRefreshTokens(String refreshToken);
    void deleteRefreshToken(String refreshToken);
    String updateRefreshToken(User user);
    Tokens generateNewUserTokens(User user);
}
