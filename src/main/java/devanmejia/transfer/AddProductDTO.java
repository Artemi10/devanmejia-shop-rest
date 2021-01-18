package devanmejia.transfer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class AddProductDTO {
    private String productName;
    private int productPrice;
    private String productDescription;
    private String productImage;
    private String productType;
}
