package devanmejia.repositories;



import devanmejia.models.entities.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ShopUser, String> {
    Optional<ShopUser> findByAuthId(String authId);
    Optional<ShopUser> findByLogin(String login);
}
