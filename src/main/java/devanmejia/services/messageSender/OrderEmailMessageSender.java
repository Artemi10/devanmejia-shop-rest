package devanmejia.services.messageSender;

import devanmejia.models.entities.Order;
import devanmejia.models.enums.EmailMessage;
import devanmejia.models.enums.OrderStatus;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;
@Service
public class OrderEmailMessageSender extends EmailMessageSender<Order>{


    @Override
    public void sendMessage(Order emailSubject) throws IOException, MessagingException {
        Message message = createOrderMessage(emailSubject);
        Transport.send(message);
    }

    private Message createOrderMessage(Order order) throws MessagingException, IOException {
        Message message = createBasicMessage(order.getUser().getEmail());
        message.setSubject("Order №" + order.getId());
        if (order.getOrderStatus().equals(OrderStatus.ORDERED)){
            message.setText(EmailMessage.SuccessfulMessage.toString());
        }
        if (order.getOrderStatus().equals(OrderStatus.READY)){
            message.setText(EmailMessage.ReadyMessage.toString());
        }
        return message;
    }
}
