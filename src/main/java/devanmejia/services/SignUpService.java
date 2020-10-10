package devanmejia.services;

import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.models.enums.UserState;
import devanmejia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class SignUpService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public void signUpUser(User user){
        String hashPassword =passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setState(UserState.ACTIVE);
        user.setUserRole(UserRole.ROLE_CLIENT);
        user.setOrders(new ArrayList<>());
        userRepository.save(user);
    }
}
