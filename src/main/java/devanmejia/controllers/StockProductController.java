package devanmejia.controllers;

import devanmejia.models.entities.Product;
import devanmejia.models.entities.StockProduct;
import devanmejia.services.product.ProductService;
import devanmejia.services.stockProduct.StockProductService;
import devanmejia.transfer.AddProductDTO;
import devanmejia.transfer.AdminStockProductDTO;
import devanmejia.transfer.StockProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/api/**")
@RestController
@CrossOrigin
public class StockProductController {
    @Autowired
    private StockProductService stockProductService;
    @Autowired
    private ProductService productService;

    @GetMapping("/stockProducts")
    public ResponseEntity<Object> showStockProducts(){
        try {
            List<StockProduct> stockProducts = stockProductService.getAllStockProducts();
            return new ResponseEntity<>(StockProductDTO.form(stockProducts), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/stockProducts")
    public ResponseEntity<Object> getStockProducts(){
        try {
            List<StockProduct> stockProducts = stockProductService.getAllStockProducts();
            return new ResponseEntity<>(AdminStockProductDTO.form(stockProducts), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/stockProduct/{productName}")
    public ResponseEntity<Object> deleteStockProducts(@PathVariable String productName) {
        try {
            stockProductService.deleteStockProducts(productName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>( "This product has already been removed", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/admin/stockProduct")
    public ResponseEntity<Object> addProductAmount(@RequestBody AdminStockProductDTO adminStockProductDTO) {
        try {
            String productName = adminStockProductDTO.getProductName();
            productService.updateProductPrice(productName, adminStockProductDTO.getPrice());
            stockProductService.updateStockProductAmount(productName, adminStockProductDTO.getAmount());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/stockProduct")
    public ResponseEntity<Object> addNewProduct(@RequestBody AddProductDTO addProduct) {
        try {
            Product product = productService.createNewProduct(addProduct);
            stockProductService.createNewStockProduct(product);
            return new ResponseEntity<>("New product was added successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
