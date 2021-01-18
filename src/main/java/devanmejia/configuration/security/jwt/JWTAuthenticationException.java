package devanmejia.configuration.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
    public JWTAuthenticationException(String msg) {
        super(msg);
    }
}
