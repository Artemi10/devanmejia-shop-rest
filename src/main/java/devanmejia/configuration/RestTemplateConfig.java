package devanmejia.configuration;

import devanmejia.configuration.interceptor.RestTemplateInterceptor;
import devanmejia.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Configuration
public class RestTemplateConfig {
    @Autowired
    private RestTemplateInterceptor restTemplateInterceptor;

    @LoadBalanced
    @Bean("authRestTemplate")
    public RestTemplate getAuthRestTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean("apiRestTemplate")
    public RestTemplate getApiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(restTemplateInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
