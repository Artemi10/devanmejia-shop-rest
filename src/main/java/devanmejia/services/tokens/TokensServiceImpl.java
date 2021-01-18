package devanmejia.services.tokens;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import devanmejia.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokensServiceImpl implements TokensService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public Tokens updateTokensByRefreshTokens(String refreshToken){
        Optional<User> userOptional = userRepository.findByRefreshToken(refreshToken);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String newRefreshToken = updateRefreshToken(user);
            String accessToken = jwtProvider.createToken(user.getLogin(), user.getUserRole());
            return new Tokens(accessToken, newRefreshToken);
        }
        throw new IllegalArgumentException("No such refresh token");
    }
    @Override
    public Tokens generateNewUserTokens(User user){
        String newRefreshToken = updateRefreshToken(user);
        String accessToken = jwtProvider.createToken(user.getLogin(), user.getUserRole());
        return new Tokens(accessToken, newRefreshToken);
    }


    @Override
    public String updateRefreshToken(User user){
        String refreshToken = RandomStringUtils.randomAlphabetic(45);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return refreshToken;
    }
    @Override
    public void deleteRefreshToken(String refreshToken){
        Optional<User> userOptional = userRepository.findByRefreshToken(refreshToken);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setRefreshToken(null);
            userRepository.save(user);
        }
        throw new IllegalArgumentException("No such refresh token");
    }

}
