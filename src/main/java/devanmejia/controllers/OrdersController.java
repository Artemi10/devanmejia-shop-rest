package devanmejia.controllers;

import devanmejia.models.entities.Order;
import devanmejia.models.enums.OrderStatus;
import devanmejia.security.jwt.JWTProvider;
import devanmejia.services.messageSender.OrderEmailMessageSender;
import devanmejia.transfer.product.OrderCartProductsDTO;
import devanmejia.services.order.OrderService;
import devanmejia.transfer.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/api/shop")
@RestController
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderEmailMessageSender orderEmailMessageSender;
    @Autowired
    private JWTProvider jwtProvider;


    @GetMapping("/orders")
    public ResponseEntity<Object> getUserOrders(HttpServletRequest request){
        try{
            String login = jwtProvider.getUserName(request);
            List<Order> orders = orderService.findOrdersByUserName(login);
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
            String login = jwtProvider.getUserName(request);
            orderService.findActiveOrder(login);
            orderEmailMessageSender.sendMessage(order);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Order was updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/admin/orders/{id}/status")
    public ResponseEntity<Object> updateOrderByOrderStatus(@PathVariable Long id, @RequestBody String status){
        try {
            Order order = orderService.updateOrderByOrderStatus(id, OrderStatus.valueOf(status.toUpperCase()));
            orderEmailMessageSender.sendMessage(order);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
