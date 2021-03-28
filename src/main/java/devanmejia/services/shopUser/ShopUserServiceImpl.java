package devanmejia.services.shopUser;

import devanmejia.models.entities.ShopUser;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShopUserServiceImpl implements ShopUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ShopUser getUserByLogin(String login) {
        Optional<ShopUser> userCandidate = userRepository.findByLogin(login);
        return userCandidate
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with name %s was not found", login)));
    }

    @Override
    public void createOrderUser(ShopUser authShopUser) {
        Optional<ShopUser> userOptional = userRepository.findByAuthId(authShopUser.getAuthId());
        if (userOptional.isEmpty()){
            authShopUser.setOrders(new ArrayList<>());
            userRepository.save(authShopUser);
        }
        else{
            throw new IllegalArgumentException(String.format("User with auth id %s has already been registered", authShopUser.getAuthId()));
        }
    }

    @Override
    public void updateUser(ShopUser shopUser) {
        userRepository.save(shopUser);
    }
}
