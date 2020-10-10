package devanmejia.controllers.clientControllers;



import devanmejia.models.entities.Order;
import devanmejia.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShowProductsController {
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping(value= "/products", method = RequestMethod.POST)
    public @ResponseBody
    List<Order> showProducts(@RequestParam int orderName){
        Optional<Order> orderOptional = orderRepository.findById(orderName);
        List<Order> orders = new ArrayList<>();
        orderOptional.ifPresent(orders::add);
        return orders;
    }
}
