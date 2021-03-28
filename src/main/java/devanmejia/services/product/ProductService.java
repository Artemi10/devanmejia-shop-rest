package devanmejia.services.product;

import devanmejia.models.entities.Product;
import devanmejia.transfer.product.AddProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ProductService {
    Product createNewProduct(AddProductDTO addProductDTO);
    Product getProductByProductName(String productName);
    void downloadProductImage(Product product);
    void deleteProductByName(String productName);
    void updateProductPrice(String productName, int price);
}
