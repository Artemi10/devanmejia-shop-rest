package devanmejia.services.order;

import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.models.entities.ShopUser;
import devanmejia.models.enums.OrderStatus;
import devanmejia.repositories.OrderRepository;
import devanmejia.services.cartProduct.CartProductService;
import devanmejia.services.product.ProductService;
import devanmejia.services.stockProduct.StockProductService;
import devanmejia.services.shopUser.ShopUserService;
import devanmejia.transfer.product.CartProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private StockProductService stockProductService;

    @Override
    public List<Order> findOrdersByUserName(String login){
        ShopUser shopUser = shopUserService.getUserByLogin(login);
        return orderRepository.findAllByShopUser(shopUser);
    }
    @Override
    public Order findActiveOrder(String login){
        List<Order> orders = findOrdersByUserName(login);
        for(Order order: orders){
            if(order.getOrderStatus().equals(OrderStatus.ACTIVE)) {
                return order;
            }
        }
        return generateNewActiveOrder(shopUserService.getUserByLogin(login));
    }

    private Order generateNewActiveOrder(ShopUser shopUser){
        Order order = Order.builder()
                .orderStatus(OrderStatus.ACTIVE)
                .cartProducts(new ArrayList<>())
                .shopUser(shopUser)
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findAllByOrderStatus(orderStatus);
    }

    @Override
    public Order updateOrderByCartProducts(Long orderId, CartProductDTO[] cartProductsDTO){
        Order order = getOrderById(orderId);
        for(CartProductDTO cartProductDTO: cartProductsDTO){
            Product product = productService.getProductByProductName(cartProductDTO.getProductName());
            setProductInOrder(product, cartProductDTO.getAmount(), order);
        }
        order.setOrderStatus(OrderStatus.ORDERED);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderByOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = getOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId){
        Optional<Order> orderCandidate = orderRepository.findById(orderId);
        if(orderCandidate.isPresent()){
            return orderCandidate.get();
        } else{
            throw new IllegalArgumentException("There is not order with id "+ orderId +" in database");
        }
    }

    @Override
    public void setProductInOrder(Product product, int amount, Order order) {
        List<CartProduct> cartProducts = order.getCartProducts();
        try {
            CartProduct cartProduct = cartProductService.getCartProductFromCartProducts(cartProducts, product.getName());
            stockProductService.changeStockProductAmount(product.getName(), amount, cartProduct.getAmount());
            cartProduct.setAmount(amount);
            cartProductService.saveCartProductInDB(cartProduct);
        }catch (IllegalArgumentException e){
            stockProductService.changeStockProductAmount(product.getName(), amount, 0);
            CartProduct newCartProduct = cartProductService.generateNewCartProduct(amount, product, order);
            cartProducts.add(newCartProduct);
            order.setCartProducts(cartProducts);
            orderRepository.save(order);
        }
    }

}
