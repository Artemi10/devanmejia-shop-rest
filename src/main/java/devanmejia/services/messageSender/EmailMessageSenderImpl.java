package devanmejia.services.messageSender;


import devanmejia.models.enums.EmailMessage;
import devanmejia.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class EmailMessageSenderImpl implements EmailMessageSender {
    @Autowired
    private OrderService orderService;
    private static Properties emailPropertiesConfig;
    private static Properties emailProperties;


    @Override
    public void sendMessage(int orderId, EmailMessage emailMessage) throws IOException, MessagingException {
         emailPropertiesConfig = loadProperties("properties/emailConfiguration.properties");
         emailProperties = loadProperties("properties/email.properties");

         Session session = Session.getDefaultInstance(emailPropertiesConfig, new Authenticator() {
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(emailProperties.getProperty("emailAddress"), emailProperties.getProperty("emailPassword"));
             }
         });

         Transport.send(createMessage(session, orderId, emailMessage));
     }

    private Properties loadProperties(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        InputStream inStreamEmailPropertiesConfig = EmailMessageSenderImpl.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inStreamEmailPropertiesConfig);
        return properties;
    }

     private Message createMessage(Session session, int orderId, EmailMessage emailMessage) throws MessagingException {
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(emailProperties.getProperty("emailAddress")));
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(getEmailByOrderId(orderId)));
         message.setSubject("Order №"+orderId);
         message.setText(emailMessage.toString());
         return message;
    }

    private String getEmailByOrderId(int orderId){
        return orderService.getOrderById(orderId).getUser().getEmail();
    }

}
