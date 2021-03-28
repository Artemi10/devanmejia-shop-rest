package devanmejia.security.jwt;


import devanmejia.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class JWTUserDetailsService implements UserDetailsService {
    @Autowired
    @Qualifier("authRestTemplate")
    private RestTemplate restTemplate;
    @Value("${auth.service.url}")
    private String authAPI;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try{
            ResponseEntity<User> userResponse = restTemplate
                    .getForEntity(authAPI + "/user/secure/" + s, User.class);
            if(userResponse.getStatusCodeValue() == 200){
                return new JWTUserDetails(userResponse.getBody());
            }
            throw new UsernameNotFoundException("User with user name " + s + " not found");
        }catch (Exception e){
            throw new UsernameNotFoundException("User with user name " + s + " not found");
        }
    }
}
