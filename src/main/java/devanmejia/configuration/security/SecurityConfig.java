package devanmejia.configuration.security;


import devanmejia.configuration.security.jwt.JWTProvider;
import devanmejia.configuration.security.jwt.JWTSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@ComponentScan("devanmejia")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/*", "/api/stockProducts").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/cartProduct/**", "/api/orders/**").hasAuthority("ROLE_CLIENT")
                .antMatchers("**/admin/**", "**/admin/cartProduct", "**/admin/orders").hasAuthority("ROLE_ADMIN")
                .and()
                .apply(new JWTSecurityConfig(jwtProvider));
        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/auth/**", "/api/stockProducts");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
