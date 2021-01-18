package devanmejia.services.cartProduct;


import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.transfer.CartProductForm;
import devanmejia.repositories.CartProductRepository;
import devanmejia.services.image.ProductImageService;
import devanmejia.services.order.OrderService;
import devanmejia.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private CartProductRepository cartProductRepository;

    @Override
    public CartProduct generateNewCartProduct(int amount, Product product, Order order){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setAmount(amount);
        cartProduct.setProduct(product);
        cartProduct.setOrder(order);
        return cartProduct;
    }

    @Override
    public void addProductInCart(CartProductForm cartProductForm, String login) {
        String productName = cartProductForm.getProductName().trim().toLowerCase();
        Product product = productService.getProductByProductName(productName);
        orderService.setProductInOrder(product, cartProductForm.getProductAmount(), orderService.findActiveOrder(login));
    }


    @Override
    public CartProduct getCartProductFromCartProducts(List<CartProduct> cartProducts, String productName){
        return cartProducts.stream().filter(cartProduct -> cartProduct.getProduct().getName().equals(productName)).findFirst()
                .orElseThrow(()->new IllegalArgumentException("There is not cartProduct with name " + productName + " in cart"));
    }

    @Override
    public void saveCartProductInDB(CartProduct cartProduct){
        cartProductRepository.save(cartProduct);
    }


    @Override
    public Map<CartProduct, byte[]> loadCartProductImage(List<CartProduct> cartProducts){
        Map<CartProduct, byte[]> result = new HashMap<>();
        cartProducts.forEach(cartProduct -> {
            try {
                result.put(cartProduct, productImageService.getImageFromDB(cartProduct.getProduct().getPicture().getURL()));
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        });
        return result;
    }

}
