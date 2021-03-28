package devanmejia.services.cartProduct;


import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.transfer.product.CartProductForm;
import devanmejia.repositories.CartProductRepository;
import devanmejia.services.order.OrderService;
import devanmejia.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
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



}
