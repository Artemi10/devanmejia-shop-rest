package devanmejia.controllers.auth;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.services.messageSender.EmailMessageSender;
import devanmejia.services.tokens.TokensService;
import devanmejia.transfer.UserLogInForm;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private TokensService tokensService;
    @Autowired
    @Qualifier("authEmailMessageSender")
    private EmailMessageSender<User> authEmailMessageSender;
    @Autowired
    @Qualifier("resetEmailMessageSender")
    private EmailMessageSender<User> resetEmailMessageSender;

    @PostMapping("/logIn")
    public ResponseEntity<String> logInUser(@RequestBody UserLogInForm logInBody){
        try {
            User user = userService.logInUser(logInBody);
            authEmailMessageSender.sendMessage(user);
            String token = jwtProvider.createToken(user.getLogin(), UserRole.ROLE_UNAUTH_USER);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Your login and password are incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logIn/userAdmin")
    public ResponseEntity<Object> logInAdmin(@RequestBody UserLogInForm userLoginForm)  {
        try {
            User admin = userService.logInAdmin(userLoginForm);
            Tokens tokens = tokensService.generateNewUserTokens(admin);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Your login and password are incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUpUser(@RequestBody UserSignUpForm signUpBody){
        try {
            User user = userService.signUpUser(signUpBody);
            Tokens tokens = tokensService.generateNewUserTokens(user);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshTokens(@RequestBody String refreshToken){
        try {
            Tokens tokens = tokensService.updateTokensByRefreshTokens(refreshToken);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logOut")
    public ResponseEntity<Object> logOut(String refreshToken){
        try {
            tokensService.deleteRefreshToken(refreshToken);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/reset")
    public ResponseEntity<String> sendResetCode(@RequestBody String login){
        try {
            User user = userService.getUserByLogin(login);
            resetEmailMessageSender.sendMessage(user);
            String token = jwtProvider.createToken(user.getLogin(), UserRole.ROLE_PASSWORD_RESET);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
