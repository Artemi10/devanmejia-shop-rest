package devanmejia.services.productHandlers;



import devanmejia.exceptions.StockException;
import devanmejia.models.entities.CartProduct;
import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.models.entities.User;
import devanmejia.models.enums.OrderStatus;
import devanmejia.repositories.CartProductRepository;
import devanmejia.repositories.OrderRepository;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartHandler {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockHandler stockHandler;

    public void setProductInOrder(Product product, int amount, Order order) throws StockException{
        List<CartProduct> cartProducts = order.getCartProduct();
        boolean flag = false;
        for(CartProduct cartProduct: cartProducts){
            if(cartProduct.getProduct().getName().equals(product.getName())){
                changeStockProductAmount(product.getName(), amount, cartProduct.getAmount());
                cartProduct.setAmount(amount);
                cartProductRepository.save(cartProduct);
                flag=true;
            }
        }
        if(!flag){
            CartProduct cartProduct = new CartProduct();
            changeStockProductAmount(product.getName(), amount, cartProduct.getAmount());
            cartProduct.setAmount(amount);
            cartProduct.setProduct(product);
            cartProduct.setOrder(order);
            cartProducts.add(cartProduct);
            order.setCartProduct(cartProducts);
            orderRepository.save(order);
        }
    }
    public void setProductAmount(Product product, int amount, String userName) throws StockException{
        Order order = createOrder( userName);
        List<CartProduct> cartProducts = order.getCartProduct();
        String productName = product.getName();
        int oldAmount;
        if(amount==0){
            try {
                CartProduct orderCartProduct = findProductInOrder(order, product);
                oldAmount = orderCartProduct.getAmount();
                cartProductRepository.deleteById(orderCartProduct.getId());
                changeStockProductAmount(productName, amount, oldAmount);
            }catch (NoSuchElementException e){
                //NOP
            }

        }
        else {
            try {
                CartProduct orderCartProduct = findProductInOrder(order, product);
                oldAmount = orderCartProduct.getAmount();
                orderCartProduct.setAmount(amount);
                cartProductRepository.save(orderCartProduct);
            }catch (NoSuchElementException e){oldAmount = 0;
                CartProduct cartProduct = new CartProduct();
                cartProduct.setProduct(product);
                cartProduct.setAmount(amount);
                cartProduct.setOrder(order);
                cartProducts.add(cartProduct);
                order.setCartProduct(cartProducts);
                orderRepository.save(order);}

            changeStockProductAmount(productName, amount, oldAmount );
        }

    }
    public void addProduct(Product product, int amount, String userName) throws StockException {
        Order order = createOrder(userName);
        List<CartProduct> cartProducts = order.getCartProduct();
        int oldAmount;
        try {
            CartProduct orderCartProduct = findProductInOrder(order, product);
            oldAmount = orderCartProduct.getAmount();
            orderCartProduct.setAmount(amount + oldAmount);
            cartProductRepository.save(orderCartProduct);
        }catch (NoSuchElementException e){
            oldAmount = 0;
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setAmount(amount + oldAmount);
            cartProduct.setOrder(order);
            cartProducts.add(cartProduct);
            order.setCartProduct(cartProducts);
            orderRepository.save(order);
        }
        changeStockProductAmount(product.getName(), amount+oldAmount, oldAmount );

    }
    private Order createOrder(String userName){
        Order order;
        try {
            order = getActualOrder(userName);
        }catch (NoSuchElementException e){
            order = new Order();
            order.setUser(userRepository.findById(userName).get());
            order.setOrderStatus(OrderStatus.RELEVANT);
            order.setCartProduct(new ArrayList<>());
            orderRepository.save(order);
            order = getActualOrder(userName);
        }
        return order;
    }

    private Order getActualOrder(String userName){
        if(userRepository.findById(userName).isPresent()) {
            User user = userRepository.findById(userName).get();
            List<Order> orders = user.getOrders();
            for (Order order : orders) {
                if (order.getOrderStatus().equals(OrderStatus.RELEVANT)) {
                    return order;
                }
            }
        }
        throw new NoSuchElementException();
    }
    private CartProduct findProductInOrder(Order order, Product product)throws NoSuchElementException{
        List<CartProduct> cartProducts = order.getCartProduct();
        for(CartProduct cartProduct : cartProducts){
            Product orderProduct = cartProduct.getProduct();
            if(product.equals(orderProduct))
                return cartProduct;
        }
        throw new NoSuchElementException();
    }
    private void changeStockProductAmount(String productName,int amount, int oldAmount) throws StockException{
        if(amount>oldAmount){
            stockHandler.removeProducts(productName, amount-oldAmount);
        }
        if(oldAmount>amount){
            stockHandler.addProducts(productName, oldAmount-amount);
        }

    }
}
