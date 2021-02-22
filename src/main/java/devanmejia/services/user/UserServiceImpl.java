package devanmejia.services.user;

import devanmejia.models.entities.User;
import devanmejia.models.enums.UserRole;
import devanmejia.models.enums.UserState;
import devanmejia.transfer.UserCodeDTO;
import devanmejia.transfer.UserLogInForm;
import devanmejia.transfer.UserSignUpForm;
import devanmejia.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
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
        try{
            User user = getUserByLogin(userForm.getLogin());
            throw new IllegalArgumentException();
        }catch (IllegalArgumentException e){
            User user = generateNewActiveUser(userForm);
            userRepository.save(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForm.getLogin(), userForm.getPassword()));
            return user;
        }
    }

    private User generateNewActiveUser(UserSignUpForm userForm){
        return new User(userForm.getLogin(), userForm.getFirstName(), userForm.getLastName(),
                passwordEncoder.encode(userForm.getPassword()), userForm.getEmail(),
                new ArrayList<>(), UserState.ACTIVE, UserRole.ROLE_CLIENT, null, null);
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
    public User getUserByLogin(String login) {
        Optional<User> userCandidate = userRepository.findById(login);
        if(userCandidate.isPresent()){
            return userCandidate.get();
        }
        else {
            throw new IllegalArgumentException("User with name " + login + " was not found");
        }
    }
    @Override
    public String generateNewLogInCode(User user){
        String code = RandomStringUtils.randomAlphanumeric(6);
        user.setCode(passwordEncoder.encode(code));
        user.setUserRole(UserRole.ROLE_UNAUTHUSER);
        userRepository.save(user);
        return code;
    }

    @Override
    public User checkUserCode(UserCodeDTO userCodeDTO) {
        Optional<User> userCandidate = userRepository.findById(userCodeDTO.getLogin());
        User user = userCandidate
                .orElseThrow(() -> new IllegalArgumentException("Login is incorrect"));
        if (passwordEncoder.matches(userCodeDTO.getCode(), user.getCode())){
            user.setUserRole(UserRole.ROLE_CLIENT);
            userRepository.save(user);
            return user;
        }
        else{
            throw new IllegalArgumentException("Code is incorrect");
        }
    }
}
