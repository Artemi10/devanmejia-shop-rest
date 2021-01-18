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
   private byte[] productImage;


    public static List<CartProductDTO> form(Map<CartProduct, byte[]> cartProductMap) {
        List<CartProductDTO> cartProductsDTO = new ArrayList<>();
        for(Map.Entry<CartProduct, byte[]> cartProductEntry: cartProductMap.entrySet()){
            CartProduct cartProduct = cartProductEntry.getKey();
            byte[] imageBytes = cartProductEntry.getValue();
            cartProductsDTO.add(generateCartProductDTO(cartProduct, imageBytes));
        }
        return cartProductsDTO;
    }

    private static CartProductDTO generateCartProductDTO(CartProduct cartProduct, byte[] imageBytes){
        return CartProductDTO.builder()
                .amount(cartProduct.getAmount())
                .productDescription(cartProduct.getProduct().getDescription())
                .productImage(imageBytes)
                .productName(cartProduct.getProduct().getName())
                .build();
    }

}
