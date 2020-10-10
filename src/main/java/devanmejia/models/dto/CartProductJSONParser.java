package devanmejia.models.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import devanmejia.models.entities.CartProduct;

import java.io.IOException;
import java.io.StringReader;

public class CartProductJSONParser {
    public static CartProduct[] getCartProductsFromJSON(String line) throws IOException {
        StringReader reader = new StringReader(line);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, CartProduct[].class);
    }
}
