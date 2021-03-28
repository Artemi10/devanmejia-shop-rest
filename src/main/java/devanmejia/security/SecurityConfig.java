package devanmejia.security;

import devanmejia.security.jwt.JWTProvider;
import devanmejia.security.jwt.JWTSecurityConfig;
import devanmejia.security.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@ComponentScan("devanmejia")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/shop/stockProducts", "/api/shop/user/create").permitAll()
                .antMatchers("/api/shop/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name())
                .antMatchers("/api/shop/cartProduct/**", "/api/orders/**").hasAuthority(UserRole.ROLE_CLIENT.name())
                .and()
                .apply(new JWTSecurityConfig(jwtProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
