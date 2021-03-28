package devanmejia.repositories;




import devanmejia.models.entities.Order;
import devanmejia.models.entities.ShopUser;
import devanmejia.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByShopUser(ShopUser shopUser);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

}
