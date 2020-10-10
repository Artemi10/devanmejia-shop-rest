package devanmejia.services.productHandlers;


import devanmejia.exceptions.NoSuchObjectInDB;
import devanmejia.exceptions.StockException;
import devanmejia.models.entities.Product;
import devanmejia.models.entities.StockProduct;
import devanmejia.repositories.ProductRepository;
import devanmejia.repositories.StockProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StockHandler {
    @Autowired
    private StockProductRepository stockProductRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addNewProduct(String productName, int amount) throws NoSuchObjectInDB {
        if(productRepository.findById(productName).isPresent()) {
            StockProduct stockProduct = new StockProduct(productName, productRepository.findById(productName).get(), amount);
            stockProductRepository.save(stockProduct);
        }else{
            throw new NoSuchObjectInDB();
        }
    }
    public  void addProducts(String productName, int amount)throws StockException{
        StockProduct stockProduct;
        if(stockProductRepository.findById(productName).isPresent()) {
            stockProduct = stockProductRepository.findById(productName).get();
            int oldAmount = stockProduct.getAmount();
            stockProduct.setAmount(oldAmount + amount);
            stockProductRepository.save(stockProduct);
        }else{
            if(productRepository.findById(productName).isPresent()) {
                Product product = productRepository.findById(productName).get();
                stockProduct = new StockProduct(productName, product, amount);
                stockProductRepository.save(stockProduct);
            }
            else {
                throw new StockException(productName+" does not exist");
            }
        }
    }
    public void addNewProductType(Product product){
        productRepository.save(product);
    }

    public  void removeProducts(String productName, int amount) throws StockException{
        if(stockProductRepository.findById(productName).isPresent()){
        StockProduct stockProduct = stockProductRepository.findById(productName).get();
        int oldAmount = stockProduct.getAmount();
        if(oldAmount>amount) {
                stockProduct.setAmount(oldAmount - amount);
                stockProductRepository.save(stockProduct);
        }
        else if(oldAmount==amount){
                stockProductRepository.deleteById(productName);
            }
        else{
                throw new StockException("There are not enough products("+productName+") in stock");
        }
        }else{
            throw new StockException(productName+" does not exist");
        }
    }

}
