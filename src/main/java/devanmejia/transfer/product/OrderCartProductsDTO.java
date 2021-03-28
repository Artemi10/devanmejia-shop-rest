package devanmejia.transfer.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartProductsDTO {
    private Long orderId;
    private CartProductDTO[] cartProducts;
}
