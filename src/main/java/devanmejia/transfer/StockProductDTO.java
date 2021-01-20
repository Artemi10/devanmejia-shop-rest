package devanmejia.transfer;


import devanmejia.models.entities.StockProduct;
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

    public static List<StockProductDTO> form(List<StockProduct> stockProducts) {
        List<StockProductDTO> stockProductsDTO = new ArrayList<>();
        for(StockProduct stockProduct: stockProducts){
            stockProductsDTO.add(generateCartProductDTO(stockProduct));
        }
        return stockProductsDTO;
    }

    private static StockProductDTO generateCartProductDTO(StockProduct stockProduct){
        return StockProductDTO.builder()
                .productDescription(stockProduct.getProduct().getDescription())
                .productImage(stockProduct.getProduct().getPicture().getPictureContent())
                .productAmount(stockProduct.getAmount())
                .productName(stockProduct.getProduct().getName())
                .productPrice(stockProduct.getProduct().getPrice())
                .productType(stockProduct.getProduct().getProductType().name())
                .build();
    }

}
