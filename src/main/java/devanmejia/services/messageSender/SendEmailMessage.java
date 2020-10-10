package devanmejia.services.messageSender;


import devanmejia.models.enums.EmailMessage;
import devanmejia.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class SendEmailMessage {
    @Autowired
    private OrderRepository orderRepository;
     public void sendMessage(int orderId, EmailMessage emailMessage) throws IOException, MessagingException {
         Properties emailPropertiesConfig = new Properties();
         Properties emailProperties = new Properties();
         InputStream inStreamEmailPropertiesConfig = SendEmailMessage.class.getClassLoader().getResourceAsStream("properties/emailConfiguration.properties");
         emailPropertiesConfig.load(inStreamEmailPropertiesConfig);
         InputStream inStreamEmailProperties = SendEmailMessage.class.getClassLoader().getResourceAsStream("properties/email.properties");
         emailProperties.load(inStreamEmailProperties);
         Session session = Session.getDefaultInstance(emailPropertiesConfig, new Authenticator() {
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(emailProperties.getProperty("emailAddress"), emailProperties.getProperty("emailPassword"));
             }
         });
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(emailProperties.getProperty("emailAddress")));
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(orderRepository.findById(orderId).get().getUser().getEmail()));
         message.setSubject("Order №"+orderId);
         message.setText(emailMessage.toString());
         Transport.send(message);


     }

}
