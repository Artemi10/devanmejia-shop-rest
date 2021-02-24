package devanmejia.configuration;

import devanmejia.services.secretCode.LogInCodeService;
import devanmejia.services.secretCode.ResetCodeService;
import devanmejia.services.secretCode.SecretCodeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SecretCodeConfig {
    @Bean(name = "resetCodeService")
    public SecretCodeService getResetCodeService(){
        return new ResetCodeService();
    }
    @Primary
    @Bean(name = "logInCodeService")
    public SecretCodeService getLogInCodeService(){
        return new LogInCodeService();
    }
}
