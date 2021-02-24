package devanmejia.controllers.auth;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/change")
public class ChangePasswordController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTProvider jwtProvider;

    @PatchMapping("/pass")
    public ResponseEntity<Object> updateUserPassword(HttpServletRequest request, @RequestBody String newPassword){
        try {
            String login = jwtProvider.getUserName(request);
            userService.updateUserPassword(login, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
