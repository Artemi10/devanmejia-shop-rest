package devanmejia.controllers.auth;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.entities.User;
import devanmejia.services.secretCode.SecretCodeService;
import devanmejia.transfer.UserCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/reset")
public class ResetController {
    @Autowired
    private JWTProvider jwtProvider;
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

}
