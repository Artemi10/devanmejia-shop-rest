package devanmejia.controllers;

import devanmejia.models.entities.StockProduct;
import devanmejia.repositories.StockProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ShowStockProductController {
    @Autowired
    private StockProductRepository stockProductRepository;
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public @ResponseBody List<StockProduct> showStockProducts(){
        return stockProductRepository.findAll();
    }
}
