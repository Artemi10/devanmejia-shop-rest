package devanmejia.controllers.adminControllers;

import devanmejia.exceptions.StockException;
import devanmejia.services.productHandlers.StockHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddProductAmountController {
    @Autowired
    private StockHandler stockHandler;
    @PostMapping("/addProductAmount")
    public @ResponseBody String addProductAmount(String productName, int productAmount){
        try {
            stockHandler.addProducts(productName, productAmount);
            return "Products were added successfully";
        }catch (StockException e){
            return e.getMessage();
        }
    }
}
