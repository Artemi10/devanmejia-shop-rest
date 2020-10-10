package devanmejia.controllers.clientControllers;

import devanmejia.models.entities.Order;
import devanmejia.repositories.OrderRepository;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ShowUserOrdersController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/showOrders")
    public @ResponseBody List<Order> showOrders(Authentication authentication){
        if (userRepository.findById(authentication.getName()).isPresent()) {
            System.out.println(userRepository.findById(authentication.getName()).get());
            return userRepository.findById(authentication.getName()).get().getOrders();
        }
        return new ArrayList<>();
    }
}
