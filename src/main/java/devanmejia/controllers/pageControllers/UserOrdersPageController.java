package devanmejia.controllers.pageControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserOrdersPageController {
    @GetMapping("/orders")
    public String getUserOrdersPage(){
        return "pages/orders.html";
    }

}
