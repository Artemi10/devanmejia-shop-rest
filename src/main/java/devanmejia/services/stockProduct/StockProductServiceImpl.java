package devanmejia.services.stockProduct;

import devanmejia.models.entities.Product;
import devanmejia.models.entities.StockProduct;
import devanmejia.repositories.StockProductRepository;
import devanmejia.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;



@Component
public class StockProductServiceImpl implements StockProductService {
    @Autowired
    private StockProductRepository stockProductRepository;
    @Autowired
    private ProductService productService;

    @Override
    public StockProduct createNewStockProduct(Product product){
        StockProduct stockProduct = new StockProduct(product.getName(), product, 0);
        stockProductRepository.save(stockProduct);
        return stockProduct;
    }

    @Override
    public List<StockProduct> getAllStockProducts(){
        return stockProductRepository.findAll();
    }

    @Override
    public StockProduct getStockProductByProductName(String productName) {
        Optional<StockProduct> stockProductCandidate = stockProductRepository.findById(productName);
        if(stockProductCandidate.isPresent()) {
            return stockProductCandidate.get();
        }
        throw new IllegalArgumentException("There is not product with name "+ productName+" in database.");
    }

    @Override
    public void deleteStockProducts(String productName){
        stockProductRepository.deleteById(productName);
        productService.deleteProductByName(productName);
    }

    @Override
    public void changeStockProductAmount(String productName,int amount, int oldAmount) {
        if(amount>oldAmount){
            removeProductsFromStock(productName, amount-oldAmount);
        }
        if(oldAmount>amount){
            addProductsInStock(productName, oldAmount-amount);
        }

    }
    @Override
    public void updateStockProductAmount(String productName, int amount) {
        StockProduct stockProduct = getStockProductByProductName(productName);
        stockProduct.setAmount(amount);
        stockProductRepository.save(stockProduct);
    }

    @Override
    public void addProductsInStock(String productName, int amount){
        StockProduct stockProduct;
        try {
            stockProduct = getStockProductByProductName(productName);
            stockProduct.setAmount(stockProduct.getAmount() + amount);
            stockProductRepository.save(stockProduct);
        }catch (IllegalArgumentException e){
            Product product = productService.getProductByProductName(productName);
            stockProduct = new StockProduct(productName, product, amount);
            stockProductRepository.save(stockProduct);
        }
    }

    @Override
    public void removeProductsFromStock(String productName, int amount){
        StockProduct stockProduct = getStockProductByProductName(productName);
        int oldAmount = stockProduct.getAmount();
        if(oldAmount > amount) {
            stockProduct.setAmount(oldAmount - amount);
            stockProductRepository.save(stockProduct);
        }
        else if(oldAmount == amount){
            stockProduct.setAmount(0);
            stockProductRepository.save(stockProduct);
        }
        else{
            throw new IllegalArgumentException("There are not enough products ("+productName+") in stock");
        }

    }
}
