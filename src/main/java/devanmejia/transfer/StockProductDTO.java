package devanmejia.transfer;

import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.StockProduct;
import devanmejia.services.image.ProductImageService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class StockProductDTO {
    private String productName;
    private int productPrice;
    private String productDescription;
    private byte[] productImage;
    private String productType;
    private int productAmount;

    public static List<StockProductDTO> form(Map<StockProduct, byte[]> stockProductMap) {
        List<StockProductDTO> stockProductsDTO = new ArrayList<>();
        for(Map.Entry<StockProduct, byte[]> stockProductEntry: stockProductMap.entrySet()){
            StockProduct stockProduct = stockProductEntry.getKey();
            byte[] imageBytes = stockProductEntry.getValue();
            stockProductsDTO.add(generateCartProductDTO(stockProduct, imageBytes));
        }
        return stockProductsDTO;
    }

    private static StockProductDTO generateCartProductDTO(StockProduct stockProduct, byte[] imageBytes){
        return StockProductDTO.builder()
                .productDescription(stockProduct.getProduct().getDescription())
                .productImage(imageBytes)
                .productAmount(stockProduct.getAmount())
                .productName(stockProduct.getProduct().getName())
                .productPrice(stockProduct.getProduct().getPrice())
                .productType(stockProduct.getProduct().getProductType().name())
                .build();
    }

}
