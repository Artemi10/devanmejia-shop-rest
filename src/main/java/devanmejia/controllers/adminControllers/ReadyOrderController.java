package devanmejia.controllers.adminControllers;

import devanmejia.models.entities.Order;
import devanmejia.models.enums.EmailMessage;
import devanmejia.models.enums.OrderStatus;
import devanmejia.repositories.OrderRepository;
import devanmejia.services.messageSender.SendEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class ReadyOrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SendEmailMessage sendEmailMessage;
    @PostMapping(value = "/readyOrder")
    public @ResponseBody String changeOrderStatus(String orderId){
        Order order = orderRepository.findById(Integer.parseInt(orderId)).get();
        order.setOrderStatus(OrderStatus.READY);
        orderRepository.save(order);
        try {
            sendEmailMessage.sendMessage(Integer.parseInt(orderId), EmailMessage.ReadyMessage);
            return "Order status was changed successfully";
        }catch (MessagingException | IOException e){
            return "Email was not sent " + e.getMessage();
        }
    }
}
