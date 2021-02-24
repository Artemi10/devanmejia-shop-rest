package devanmejia.services.user;

import devanmejia.models.entities.User;
import devanmejia.transfer.UserLogInForm;
import devanmejia.transfer.UserSignUpForm;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    @Async
    void updateUser(User user);
    User signUpUser(UserSignUpForm userForm);
    User logInUser(UserLogInForm userLoginForm);
    User logInAdmin(UserLogInForm userLoginForm);
    User getUserByLogin(String login);
    User updateUserPassword(String login, String password);
}
