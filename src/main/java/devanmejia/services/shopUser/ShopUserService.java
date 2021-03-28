package devanmejia.services.shopUser;

import devanmejia.models.entities.ShopUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public interface ShopUserService {
    @Async
    void updateUser(ShopUser shopUser);
    ShopUser getUserByLogin(String login);
    void createOrderUser(ShopUser authShopUser);
}
