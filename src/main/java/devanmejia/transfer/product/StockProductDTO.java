package devanmejia.transfer.product;



import devanmejia.models.entities.StockProduct;
import devanmejia.transfer.image.DownloadedImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class StockProductDTO {
    private String productName;
    private int productPrice;
    private String productDescription;
    private String productImage;
    private String productType;
    private int productAmount;

    public static List<StockProductDTO> form(List<StockProduct> stockProducts, DownloadedImage[] downloadedImages) {
        List<StockProductDTO> stockProductsDTO = new ArrayList<>();
        for(int i = 0; i < stockProducts.size(); i++){
            stockProductsDTO.add(generateCartProductDTO(stockProducts.get(i), downloadedImages[i].getLink()));
        }
        return stockProductsDTO;
    }

    private static StockProductDTO generateCartProductDTO(StockProduct stockProduct, String link){
        return StockProductDTO.builder()
                .productDescription(stockProduct.getProduct().getDescription())
                .productAmount(stockProduct.getAmount())
                .productName(stockProduct.getProduct().getName())
                .productPrice(stockProduct.getProduct().getPrice())
                .productType(stockProduct.getProduct().getProductType().name())
                .productImage(link)
                .build();
    }

}
