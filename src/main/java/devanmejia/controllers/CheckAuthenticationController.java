package devanmejia.controllers;


import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.repositories.UserRepository;
import devanmejia.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class CheckAuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/checkAuthentication")
    public @ResponseBody String checkAuthentication(Authentication authentication){
        if(authentication!=null){
            Optional<User> userCandidate = userRepository.findById( authentication.getName());
            if(userCandidate.isPresent()){
                User user = userCandidate.get();
                if(user.getUserRole().equals(UserRole.ROLE_CLIENT))
                    return "user="+user.getFirstName()+"="+user.getLastName();
                else return "admin";
            }
            else {
                authentication.setAuthenticated(false);
                return "There is no authentication";
            }

        }
            return "There is no authentication";
    }
}
