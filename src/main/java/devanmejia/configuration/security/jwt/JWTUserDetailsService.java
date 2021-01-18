package devanmejia.configuration.security.jwt;


import devanmejia.models.entities.User;
import devanmejia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findById(s);
        if(userCandidate.isPresent())
            return new JWTUserDetails(userCandidate.get());
        else throw new UsernameNotFoundException("User with user name "+ s+" not found");
    }
}
