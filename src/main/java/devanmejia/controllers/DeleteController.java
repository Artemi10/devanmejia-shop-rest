package devanmejia.controllers;

import devanmejia.models.entities.User;
import devanmejia.models.enums.UserState;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeleteController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/delete")
    public String delete(Authentication authentication){
        if(authentication!=null) {
            if (userRepository.findById(authentication.getName()).isPresent()){
                User user =userRepository.findById(authentication.getName()).get();
                user.setState(UserState.DELETED);
                userRepository.save(user);
            }
            authentication.setAuthenticated(false);
        }
        return "redirect:/logout";
    }
}
