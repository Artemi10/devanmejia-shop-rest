package devanmejia.controllers;


import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.security.jwt.JWTProvider;
import devanmejia.services.product.ProductService;
import devanmejia.transfer.product.CartProductForm;
import devanmejia.services.cartProduct.CartProductService;
import devanmejia.services.order.OrderService;
import devanmejia.transfer.product.CartProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/api/shop")
@RestController
public class CartProductController {
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
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
    public ResponseEntity<Object> getAdminCartProducts(@PathVariable Long orderId){
        try {
            Order order = orderService.getOrderById(orderId);
            List<CartProduct> cartProducts = order.getCartProducts();
            cartProducts.forEach(cartProduct -> productService.downloadProductImage(cartProduct.getProduct()));
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cartProduct/{orderId}")
    public ResponseEntity<Object> getCartProducts(@PathVariable Long orderId){
        try {
            Order order = orderService.getOrderById(orderId);
            List<CartProduct> cartProducts = order.getCartProducts();
            cartProducts.forEach(cartProduct -> productService.downloadProductImage(cartProduct.getProduct()));
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cartProduct/active")
    public ResponseEntity<Object> getCartProductsFromActiveOrder(HttpServletRequest request){
        try {
            String login = jwtProvider.getUserName(request);
            Order order = orderService.findActiveOrder(login);
            List<CartProduct> cartProducts = order.getCartProducts();
            cartProducts.forEach(cartProduct -> productService.downloadProductImage(cartProduct.getProduct()));
            return new ResponseEntity<>(CartProductDTO.form(order.getCartProducts()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
