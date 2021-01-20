package devanmejia.services.product;

import devanmejia.models.entities.Picture;
import devanmejia.models.entities.Product;
import devanmejia.models.enums.ProductType;
import devanmejia.repositories.ProductRepository;
import devanmejia.services.image.ProductImageService;
import devanmejia.transfer.AddProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageService productImageService;

    @Override
    public Product createNewProduct(AddProductDTO addProductDTO) {
        String productURL = addProductDTO.getProductName()+".jpg";
        Product product = Product.builder()
                .description(addProductDTO.getProductDescription())
                .name(addProductDTO.getProductName())
                .productType(ProductType.valueOf(addProductDTO.getProductType().toUpperCase()))
                .price(addProductDTO.getProductPrice())
                .picture(new Picture(addProductDTO.getProductName()+"_picture", productURL))
                .build();
        try {
            productImageService.loadImageInDB(addProductDTO.getProductImage().split(",")[1].getBytes(), productURL);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
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
