package devanmejia.controllers.auth;

import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import devanmejia.services.messageSender.EmailMessageSender;
import devanmejia.services.secretCode.SecretCodeService;
import devanmejia.services.tokens.TokensService;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/verify/")
public class VerificationController {
    @Autowired
    private EmailMessageSender<User> emailMessageSender;
    @Autowired
    private UserService userService;
    @Autowired
    private TokensService tokensService;
    @Autowired
    @Qualifier("logInCodeService")
    private SecretCodeService logInCodeService;

    @PostMapping("/code")
    public ResponseEntity<Object> checkLogInUser(@RequestBody UserCodeDTO userCodeDTO){
        try {
            User user = logInCodeService.checkUserCode(userCodeDTO);
            Tokens tokens = tokensService.generateNewUserTokens(user);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/code/refresh")
    public ResponseEntity<Object> updateVerifyCode(@RequestBody String login){
        try {
            User user = userService.getUserByLogin(login);
            emailMessageSender.sendMessage(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
