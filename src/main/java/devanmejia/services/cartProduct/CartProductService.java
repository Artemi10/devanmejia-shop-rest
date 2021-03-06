package devanmejia.services.cartProduct;

import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.transfer.product.CartProductForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartProductService {
    CartProduct generateNewCartProduct(int amount, Product product, Order order);
    void addProductInCart(CartProductForm cartProductForm, String login);
    void saveCartProductInDB(CartProduct cartProduct);
    CartProduct getCartProductFromCartProducts(List<CartProduct> cartProducts, String productName);
}
