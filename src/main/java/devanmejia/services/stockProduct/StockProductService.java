package devanmejia.services.stockProduct;

import devanmejia.models.entities.Product;
import devanmejia.models.entities.StockProduct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface StockProductService {
    StockProduct createNewStockProduct(Product product);
    List<StockProduct> getAllStockProducts();
    StockProduct getStockProductByProductName(String productName);
    void deleteStockProducts(String productName) throws IOException;
    void changeStockProductAmount(String productName,int amount, int oldAmount);
    void addProductsInStock(String productName, int amount);
    void updateStockProductAmount(String productName, int amount);
    void removeProductsFromStock(String productName, int amount);
    Map<StockProduct, byte[]> loadStockProductImage(List<StockProduct> stockProducts);
}
