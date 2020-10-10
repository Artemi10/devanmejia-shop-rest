package devanmejia.controllers.pageControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/admin")
    public String getAdminPage(){
        return "pages/admin.html";
    }

}
