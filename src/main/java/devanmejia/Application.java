package devanmejia;

import com.fasterxml.jackson.databind.ObjectMapper;
import devanmejia.models.entities.CartProduct;
import devanmejia.transfer.CartProductDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("devanmejia")
public class Application {


    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
