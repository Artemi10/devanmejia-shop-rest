package devanmejia.services.product;

import devanmejia.models.entities.Product;
import devanmejia.models.enums.ProductType;
import devanmejia.repositories.ProductRepository;
import devanmejia.services.storage.ImageStorage;
import devanmejia.transfer.product.AddProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public Product createNewProduct(AddProductDTO addProductDTO) {
        Product product = Product.builder()
                .description(addProductDTO.getProductDescription())
                .name(addProductDTO.getProductName())
                .productType(ProductType.valueOf(addProductDTO.getProductType().toUpperCase()))
                .price(addProductDTO.getProductPrice())
                .build();
        imageStorage.uploadProductImage(addProductDTO.getProductName(), addProductDTO.getProductImage());
        return productRepository.save(product);
    }

    @Override
    public Product getProductByProductName(String productName) {
        Optional<Product> productOptional = productRepository.getProductByName(productName);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        else{
            throw new IllegalArgumentException("There is not product with name " + productName + " in database");
        }
    }

    @Override
    public void deleteProductByName(String productName) {
        productRepository.deleteProductByName(productName);
    }

    @Override
    public void updateProductPrice(String productName, int price) {
        Product product = getProductByProductName(productName);
        product.setPrice(price);
        productRepository.save(product);
    }

    @Override
    public void downloadProductImage(Product product) {
        String link = imageStorage.downloadProductImage(product.getName());
        product.setImageLink(link);
    }
}
