package devanmejia.controllers;


import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.entities.Order;
import devanmejia.transfer.CartProductForm;
import devanmejia.services.cartProduct.CartProductService;
import devanmejia.services.order.OrderService;
import devanmejia.transfer.CartProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api")
@CrossOrigin
@RestController
public class CartProductController {
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/cartProduct")
    public ResponseEntity<Object> addProductInCart(@RequestBody CartProductForm cartProductForm, HttpServletRequest request) {
        try {
            String login = jwtProvider.getUserName(request);
            cartProductService.addProductInCart(cartProductForm, login);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/cartProduct/{orderId}")
    public ResponseEntity<Object> getAdminCartProducts(@PathVariable int orderId){
        try {
            Order order = orderService.getOrderById(orderId);
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cartProduct/{orderId}")
    public ResponseEntity<Object> getCartProducts(@PathVariable int orderId){
        try {
            Order order = orderService.getOrderById(orderId);
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cartProduct/active")
    public ResponseEntity<Object> getCartProductsFromActiveOrder(HttpServletRequest request){
        try {
            Order order = orderService.findActiveOrder(jwtProvider.getUserName(request));
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
