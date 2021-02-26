package devanmejia.controllers.auth;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.entities.User;
import devanmejia.services.messageSender.EmailMessageSender;
import devanmejia.services.secretCode.SecretCodeService;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;


@CrossOrigin
@RestController
@RequestMapping("/api/reset")
public class ResetController {
    @Autowired
    @Qualifier("resetEmailMessageSender")
    private EmailMessageSender<User> emailMessageSender;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("resetCodeService")
    private SecretCodeService resetCodeService;

    @PostMapping("/code")
    public ResponseEntity<Object> checkResetCode(@RequestBody UserCodeDTO userCodeDTO){
        try {
            User user = resetCodeService.checkUserCode(userCodeDTO);
            String token = jwtProvider.createToken(user.getLogin(), user.getUserRole());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PatchMapping("/code")
    public ResponseEntity<Object> updateResetCode(@RequestBody String login){
        try {
            User user = userService.getUserByLogin(login);
            emailMessageSender.sendMessage(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException | IOException | MessagingException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
