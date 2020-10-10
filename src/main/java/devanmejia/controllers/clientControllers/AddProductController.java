package devanmejia.controllers.clientControllers;


import devanmejia.exceptions.StockException;
import devanmejia.repositories.ProductRepository;
import devanmejia.services.productHandlers.CartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AddProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartHandler cartHandler;
    @PostMapping("/add")
    public @ResponseBody String addProductInCart(@RequestParam String productName, Authentication authentication) {
        if (authentication != null) {
            String login = authentication.getName();
            try {
                productName = productName.trim().toLowerCase();
                if (productRepository.findById(productName).isPresent()) {
                    cartHandler.addProduct(productRepository.findById(productName).get(), 1, login);
                    return "You added  " + productName.trim().toLowerCase() + " to shopping cart. Do you want to place the order?";
                } else return "Please, sign up";
            } catch (StockException e) {
                return e.getMessage();
            }
        }
        return "Please, sign up";
    }
}
