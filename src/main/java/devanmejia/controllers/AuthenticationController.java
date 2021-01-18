package devanmejia.controllers;

import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import devanmejia.services.tokens.TokensService;
import devanmejia.transfer.UserLogInForm;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokensService tokensService;

    @PostMapping("/logIn")
    public ResponseEntity<Object> logInUser(@RequestBody UserLogInForm logInBody){
        try {
            User user = userService.logInUser(logInBody);
            Tokens tokens = tokensService.generateNewUserTokens(user);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Your login and password are incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/admin/logIn" )
    public ResponseEntity<Object> logInAdmin(@RequestBody UserLogInForm userLoginForm)  {
        try {
            User admin = userService.logInAdmin(userLoginForm);
            Tokens tokens = tokensService.generateNewUserTokens(admin);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Your login and password are incorrect", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUpUser(@RequestBody UserSignUpForm signUpBody){
        try {
            User user = userService.signUpUser(signUpBody);
            Tokens tokens = tokensService.generateNewUserTokens(user);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(String.format("User %s has already been registered", signUpBody.getLogin()), HttpStatus.UNAUTHORIZED);
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
}