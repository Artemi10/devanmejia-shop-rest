package devanmejia.controllers.adminControllers;


import devanmejia.repositories.ProductRepository;
import devanmejia.repositories.StockProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class DeleteProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockProductRepository stockProductRepository;
    @PostMapping("/deleteProduct")
    public @ResponseBody String deleteProduct(@RequestParam("deleteProductName") String productName){
        try {
            stockProductRepository.deleteById(productName);
            productRepository.deleteById(productName);
        }catch(NoSuchElementException e){
            return "This product has already been removed";
        }
        return "Product was deleted successfully";
    }
}
