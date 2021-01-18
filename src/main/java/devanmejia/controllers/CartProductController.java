package devanmejia.controllers;


import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.models.entities.CartProduct;
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
import java.util.List;
import java.util.Map;


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
    public ResponseEntity<List<CartProductDTO>> getAdminCartProducts(@PathVariable int orderId){
        Order order = orderService.getOrderById(orderId);
        Map<CartProduct, byte[]> cartProductMap = cartProductService.loadCartProductImage(order.getCartProducts());
        return new ResponseEntity<>(CartProductDTO.form(cartProductMap), HttpStatus.OK);
    }

    @GetMapping("/cartProduct/{orderId}")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(@PathVariable int orderId){
        Order order = orderService.getOrderById(orderId);
        Map<CartProduct, byte[]> cartProductMap = cartProductService.loadCartProductImage(order.getCartProducts());
        return new ResponseEntity<>(CartProductDTO.form(cartProductMap), HttpStatus.OK);
    }

    @GetMapping("/cartProduct/active")
    public ResponseEntity<List<CartProductDTO>> getCartProductsFromActiveOrder(HttpServletRequest request){
        Order order = orderService.findActiveOrder(jwtProvider.getUserName(request));
        Map<CartProduct, byte[]> cartProductMap = cartProductService.loadCartProductImage(order.getCartProducts());
        return new ResponseEntity<>(CartProductDTO.form(cartProductMap), HttpStatus.OK);
    }

}
