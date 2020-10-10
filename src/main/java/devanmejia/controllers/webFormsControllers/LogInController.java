package devanmejia.controllers.webFormsControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LogInController {
    @GetMapping("/logIn")
    public String getLogInPage(HttpServletRequest request, Authentication authentication){
        if(authentication!=null){
            return "redirect:/home";
        }
        if(request.getParameterMap().containsKey("error"))
            return "redirect:/signUp";
        return "/pages/logIn1.html";
    }


}
