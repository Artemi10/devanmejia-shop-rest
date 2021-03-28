package devanmejia.services.messageSender;

import devanmejia.models.entities.Order;
import devanmejia.transfer.order.EmailOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderEmailMessageSender {
    @Autowired
    @Qualifier("apiRestTemplate")
    private RestTemplate restTemplate;
    @Value("${email.sender.api.url}")
    private String emailSenderAPI;

    @Async
    public void sendMessage(Order order){
        EmailOrderDTO emailOrder = EmailOrderDTO.builder()
                .id(order.getId())
                .email(order.getShopUser().getEmail())
                .orderStatus(order.getOrderStatus().name()).build();
        try{
            restTemplate.postForEntity(emailSenderAPI + "/order", emailOrder, Object.class);
        }
        catch (Exception e){
            throw new IllegalArgumentException(
                    String.format("Can not send email to %s. " + e.getMessage(), emailOrder.getEmail()));
        }

    }
}
