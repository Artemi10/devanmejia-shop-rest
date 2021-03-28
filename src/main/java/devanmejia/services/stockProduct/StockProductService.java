package devanmejia.services.stockProduct;

import devanmejia.models.entities.Product;
import devanmejia.models.entities.StockProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface StockProductService {
    StockProduct createNewStockProduct(Product product);
    List<StockProduct> getAllStockProductsOrderByName();
    StockProduct getStockProductByProductName(String productName);
    @Transactional
    void deleteStockProducts(String productName);
    void changeStockProductAmount(String productName,int amount, int oldAmount);
    void addProductsInStock(String productName, int amount);
    void updateStockProductAmount(String productName, int amount);
    void removeProductsFromStock(String productName, int amount);
}
