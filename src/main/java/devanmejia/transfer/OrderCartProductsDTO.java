package devanmejia.transfer;

import devanmejia.transfer.CartProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartProductsDTO {
    private int orderId;
    private CartProductDTO[] cartProducts;
}
