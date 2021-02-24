package devanmejia.services.user;

import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.models.enums.UserState;
import devanmejia.transfer.UserLogInForm;
import devanmejia.transfer.UserSignUpForm;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User signUpUser(UserSignUpForm userForm){
        Optional<User> userCandidate = userRepository.findById(userForm.getLogin());
        if (userCandidate.isEmpty()){
            User user = generateNewActiveUser(userForm);
            updateUser(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForm.getLogin(), userForm.getPassword()));
            return user;
        }
        else{
            throw new IllegalArgumentException(String.format("User %s has already been registered", userForm.getLogin()));
        }
    }

    private User generateNewActiveUser(UserSignUpForm userForm){
        return User.builder()
                .login(userForm.getLogin())
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .email(userForm.getEmail())
                .orders(new ArrayList<>())
                .userRole(UserRole.ROLE_CLIENT)
                .state(UserState.ACTIVE).build();
    }

    @Override
    public User logInUser(UserLogInForm userLoginForm) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginForm.getLogin(), userLoginForm.getPassword()));
        return getUserByLogin(userLoginForm.getLogin());
    }

    @Override
    public User logInAdmin(UserLogInForm userLoginForm) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginForm.getLogin(), userLoginForm.getPassword()));
        User user = getUserByLogin(userLoginForm.getLogin());
        if(user.getUserRole().equals(UserRole.ROLE_ADMIN)){
            return user;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public User updateUserPassword(String login, String password) {
        User user = getUserByLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        updateUser(user);
        return user;
    }
    @Override
    public User getUserByLogin(String login) {
        Optional<User> userCandidate = userRepository.findById(login);
        return userCandidate
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with name %s was not found", login)));
    }
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
