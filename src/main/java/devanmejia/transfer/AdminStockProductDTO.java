package devanmejia.transfer;

import devanmejia.models.entities.Order;
import devanmejia.models.entities.StockProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@Component
@AllArgsConstructor
public class AdminStockProductDTO {
    private String productName;
    private int amount;
    private int price;

    public static AdminStockProductDTO form(StockProduct stockProduct){
        return AdminStockProductDTO.builder()
                .productName(stockProduct.getProductName())
                .amount(stockProduct.getAmount())
                .price(stockProduct.getProduct().getPrice())
                .build();
    }

    public static List<AdminStockProductDTO> form(List<StockProduct> stockProducts){
        List<AdminStockProductDTO> result = new ArrayList<>();
        stockProducts.forEach(stockProduct -> result.add(form(stockProduct)));
        return result;
    }
}
