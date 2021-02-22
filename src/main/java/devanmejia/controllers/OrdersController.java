package devanmejia.controllers;

import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.entities.Order;
import devanmejia.models.enums.EmailMessage;
import devanmejia.models.enums.OrderStatus;
import devanmejia.transfer.OrderCartProductsDTO;
import devanmejia.services.messageSender.EmailMessageSender;
import devanmejia.services.order.OrderService;
import devanmejia.transfer.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RequestMapping("/api")
@CrossOrigin
@RestController
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailMessageSender<Order> orderEmailMessageSender;
    @Autowired
    private JWTProvider jwtProvider;


    @GetMapping("/orders")
    public ResponseEntity<Object> getUserOrders(HttpServletRequest request){
        try{
            List<Order> orders = orderService.findOrdersByUserName(jwtProvider.getUserName(request));
            return new ResponseEntity<>(OrderDTO.form(orders), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<Object> getAllOrders() {
        try{
            List<Order> orderedOrders = orderService.findOrdersByStatus(OrderStatus.ORDERED);
            List<Order> readyOrders = orderService.findOrdersByStatus(OrderStatus.READY);
            orderedOrders.addAll(readyOrders);
            return new ResponseEntity<>(OrderDTO.form(orderedOrders), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/orders/products")
    public ResponseEntity<String> updateOrderByCartProducts(@RequestBody OrderCartProductsDTO orderCartProductsDTO, HttpServletRequest request) {
        try {
            Order order = orderService.updateOrderByCartProducts(orderCartProductsDTO.getOrderId(), orderCartProductsDTO.getCartProducts());
            orderService.findActiveOrder(jwtProvider.getUserName(request));
            orderEmailMessageSender.sendMessage(order);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch(MessagingException | IOException ex){
            return new ResponseEntity<>(ex.getMessage() + " Order was updated successfully", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Order was updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/admin/orders/{id}/status")
    public ResponseEntity<Object> updateOrderByOrderStatus(@PathVariable int id, @RequestBody String status){
        try {
            Order order = orderService.updateOrderByOrderStatus(id, OrderStatus.valueOf(status.toUpperCase()));
            orderEmailMessageSender.sendMessage(order);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
