package devanmejia.services.product;

import devanmejia.models.entities.Product;
import devanmejia.transfer.AddProductDTO;
import devanmejia.transfer.StockProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    String createNewProduct(AddProductDTO addProductDTO);
    Product getProductByProductName(String productName);
    void deleteProductByName(String productName);
    void updateProductPrice(String productName, int price);

}
