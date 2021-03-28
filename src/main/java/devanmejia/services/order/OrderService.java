package devanmejia.services.order;

import devanmejia.models.entities.Order;
import devanmejia.models.entities.Product;
import devanmejia.models.enums.OrderStatus;
import devanmejia.transfer.product.CartProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> findOrdersByUserName(String login);
    List<Order> findOrdersByStatus(OrderStatus orderStatus);
    Order findActiveOrder(String login);
    Order updateOrderByCartProducts(Long orderId, CartProductDTO[] cartProductsDTO);
    Order updateOrderByOrderStatus(Long orderId, OrderStatus orderStatus);
    Order getOrderById(Long orderId);
    void setProductInOrder(Product product, int amount, Order order);

}
