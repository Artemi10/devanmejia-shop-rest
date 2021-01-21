package devanmejia.transfer;


import devanmejia.models.entities.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@Component
@AllArgsConstructor
public class CartProductDTO {
   private String productName;
   private String productDescription;
   private int amount;
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
                .productImage(cartProduct.getProduct().getPicture().getPictureContent())
                .productName(cartProduct.getProduct().getName())
                .build();
    }

}
