package devanmejia.controllers.webFormsControllers;

import devanmejia.models.entities.User;
import devanmejia.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/signUp")
    public String getSignUpPage(Authentication authentication){
        if(authentication!=null)
            return "redirect:/home";
        return "/pages/signUp1.html";
    }
    @PostMapping("/signUp")
    public String signUpUser(User user){
        signUpService.signUpUser(user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, user.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        return "redirect:/home";
    }
}
