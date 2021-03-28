package devanmejia.transfer.product;


import devanmejia.models.entities.CartProduct;
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
public class CartProductDTO {
    private String productName;
    private String productDescription;
    private int amount;
    private int productPrice;
    private String productImage;


    public static List<CartProductDTO> form(List<CartProduct> cartProducts) {
        List<CartProductDTO> cartProductsDTO = new ArrayList<>();
        for(CartProduct cartProduct: cartProducts){
            cartProductsDTO.add(generateCartProductDTO(cartProduct));
        }
        return cartProductsDTO;
    }

    private static CartProductDTO generateCartProductDTO(CartProduct cartProduct){
        return CartProductDTO.builder()
                .amount(cartProduct.getAmount())
                .productDescription(cartProduct.getProduct().getDescription())
                .productPrice(cartProduct.getProduct().getPrice())
                .productName(cartProduct.getProduct().getName())
                .productImage(cartProduct.getProduct().getImageLink())
                .build();
    }

}
