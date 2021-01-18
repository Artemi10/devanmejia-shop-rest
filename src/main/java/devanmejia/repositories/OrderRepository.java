package devanmejia.repositories;




import devanmejia.models.entities.Order;
import devanmejia.models.entities.User;
import devanmejia.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

}
