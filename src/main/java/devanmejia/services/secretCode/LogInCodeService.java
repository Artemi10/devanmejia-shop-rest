package devanmejia.services.secretCode;

import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.services.user.UserService;
import devanmejia.transfer.UserCodeDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LogInCodeService implements SecretCodeService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generateNewCode(User user) {
        String code = RandomStringUtils.randomAlphanumeric(6);
        user.setLogInCode(passwordEncoder.encode(code));
        user.setUserRole(UserRole.ROLE_UNAUTH_USER);
        userService.updateUser(user);
        return code;
    }

    @Override
    public User checkUserCode(UserCodeDTO userCodeDTO) {
        User user = userService.getUserByLogin(userCodeDTO.getLogin());
        if (passwordEncoder.matches(userCodeDTO.getCode(), user.getLogInCode())){
            user.setUserRole(UserRole.ROLE_CLIENT);
            userService.updateUser(user);
            return user;
        }
        else{
            throw new IllegalArgumentException("Code is incorrect");
        }
    }
}
