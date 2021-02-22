package devanmejia.controllers;

import devanmejia.models.Tokens;
import devanmejia.models.entities.User;
import devanmejia.services.tokens.TokensService;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/verify/")
public class VerificationController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokensService tokensService;

    @PostMapping("/code")
    public ResponseEntity<Object> checkLogInUser(@RequestBody UserCodeDTO userCodeDTO){
        try {
            User user = userService.checkUserCode(userCodeDTO);
            Tokens tokens = tokensService.generateNewUserTokens(user);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
