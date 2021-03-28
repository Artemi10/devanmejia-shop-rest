package devanmejia.transfer.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductDTO {
    private String productName;
    private int productPrice;
    private String productDescription;
    private byte[] productImage;
    private String productType;
}
