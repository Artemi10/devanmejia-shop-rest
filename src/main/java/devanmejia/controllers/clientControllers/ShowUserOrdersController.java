package devanmejia.controllers.clientControllers;

import devanmejia.models.entities.Order;
import devanmejia.models.entities.User;
import devanmejia.repositories.OrderRepository;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShowUserOrdersController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/showOrders")
    public @ResponseBody List<Order> showOrders(Authentication authentication){
        Optional<User> userCandidate = userRepository.findById(authentication.getName());
        if( userCandidate.isPresent())
            return orderRepository.findAllByUser(userCandidate.get());
        return new ArrayList<>();
    }
}
