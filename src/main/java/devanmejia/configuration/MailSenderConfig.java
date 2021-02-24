package devanmejia.configuration;

import devanmejia.models.entities.User;
import devanmejia.services.messageSender.AuthEmailMessageSender;
import devanmejia.services.messageSender.EmailMessageSender;
import devanmejia.services.messageSender.ResetEmailMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MailSenderConfig {
    @Bean(name = "resetEmailMessageSender")
    public EmailMessageSender<User> getResetEmailMessageSender(){
        return new ResetEmailMessageSender();
    }

    @Primary
    @Bean(name = "authEmailMessageSender")
    public EmailMessageSender<User> getAuthEmailMessageSender(){
        return new AuthEmailMessageSender();
    }
}
