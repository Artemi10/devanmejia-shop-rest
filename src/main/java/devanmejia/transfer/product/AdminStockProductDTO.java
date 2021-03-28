package devanmejia.transfer.product;

import devanmejia.models.entities.StockProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AdminStockProductDTO {
    private String productName;
    private int amount;
    private int price;

    public static AdminStockProductDTO form(StockProduct stockProduct){
        return AdminStockProductDTO.builder()
                .productName(stockProduct.getProduct().getName())
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
