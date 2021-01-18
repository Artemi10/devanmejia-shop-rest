package devanmejia.services.messageSender;

import devanmejia.models.enums.EmailMessage;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailMessageSender {
    void sendMessage(int orderId, EmailMessage emailMessage) throws IOException, MessagingException;
}
