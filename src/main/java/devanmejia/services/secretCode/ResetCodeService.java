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
public class ResetCodeService implements SecretCodeService{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generateNewCode(User user) {
        String code = RandomStringUtils.randomAlphanumeric(10);
        user.setResetCode(passwordEncoder.encode(code));
        user.setUserRole(UserRole.ROLE_PASSWORD_RESET);
        userService.updateUser(user);
        return code;
    }

    @Override
    public User checkUserCode(UserCodeDTO userCodeDTO) {
        User user = userService.getUserByLogin(userCodeDTO.getLogin());
        if (passwordEncoder.matches(userCodeDTO.getCode(), user.getResetCode())){
            user.setUserRole(UserRole.ROLE_RESET_ALLOWED);
            userService.updateUser(user);
            return user;
        }
        else{
            throw new IllegalArgumentException("Code is incorrect");
        }
    }
}
