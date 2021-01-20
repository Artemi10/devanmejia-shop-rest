package devanmejia.services.product;

import devanmejia.models.entities.Picture;
import devanmejia.models.entities.Product;
import devanmejia.models.enums.ProductType;
import devanmejia.repositories.ProductRepository;
import devanmejia.transfer.AddProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createNewProduct(AddProductDTO addProductDTO) {
        byte[] imageBytes = addProductDTO.getProductImage().split(",")[1].getBytes();
        String productURL = addProductDTO.getProductName()+".jpg";
        Product product = Product.builder()
                .description(addProductDTO.getProductDescription())
                .name(addProductDTO.getProductName())
                .productType(ProductType.valueOf(addProductDTO.getProductType().toUpperCase()))
                .price(addProductDTO.getProductPrice())
                .picture(new Picture(addProductDTO.getProductName()+"_picture", productURL, imageBytes))
                .build();
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductByProductName(String productName) {
        Optional<Product> productOptional = productRepository.findById(productName);
        if (productOptional.isPresent()){
            return productOptional.get();
        }
        else{
            throw new IllegalArgumentException("There is not product with name " + productName + " in database");
        }
    }

    @Override
    public void deleteProductByName(String productName) {
        productRepository.deleteById(productName);
    }

    @Override
    public void updateProductPrice(String productName, int price) {
        Product product = getProductByProductName(productName);
        product.setPrice(price);
        productRepository.save(product);
    }
}
