package devanmejia.controllers.clientControllers;

import devanmejia.exceptions.StockException;
import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.models.enums.EmailMessage;
import devanmejia.models.enums.OrderStatus;
import devanmejia.repositories.OrderRepository;
import devanmejia.repositories.ProductRepository;
import devanmejia.services.messageSender.SendEmailMessage;
import devanmejia.services.productHandlers.CartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

import static devanmejia.models.dto.CartProductJSONParser.getCartProductsFromJSON;

@Controller
public class PlaceOrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartHandler cartHandler;
    @Autowired
    private SendEmailMessage sendEmailMessage;

    @PostMapping(value = "/placeOrder", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody String placeOrder(String orderId, String products) throws IOException {
        int orderName = Integer.parseInt(orderId.split(":")[1]);
        for(CartProduct cartProduct: getCartProductsFromJSON(products)){
            Product product = cartProduct.getProduct();
            if(orderRepository.findById(orderName).isPresent()) {
                try {
                    Order order = orderRepository.findById(orderName).get();
                    cartHandler.setProductInOrder(product, cartProduct.getAmount(), order);
                    order.setOrderStatus(OrderStatus.IRRELEVANT);
                    orderRepository.save(order);
                }catch(StockException e){return e.getMessage();}
            }
        }
        try {
            sendEmailMessage.sendMessage(orderName, EmailMessage.SuccessfulMessage);
        }catch (MessagingException | IOException ex){return ex.getMessage(); }

        return "success";
    }
}
