package devanmejia.controllers.pageControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showHomePage(){
          return "pages/shop1.html";
    }
}
