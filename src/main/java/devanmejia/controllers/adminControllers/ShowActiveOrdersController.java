package devanmejia.controllers.adminControllers;



import devanmejia.models.entities.Order;
import devanmejia.models.enums.OrderStatus;
import devanmejia.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ShowActiveOrdersController {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/showActiveOrders")
    public @ResponseBody Object showActiveOrders(){
        List<Order> orders = orderRepository.findAll();
        List<Order> activeOrders = new ArrayList<>();
        for(Order order: orders){
            if(order.getOrderStatus().equals(OrderStatus.IRRELEVANT)){
                activeOrders.add(order);
            }
        }
        if(activeOrders.size()==0){
            return "There is no active orders at the moment";
        }
        else{
            return orders;
        }
    }
}
