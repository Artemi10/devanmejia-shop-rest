package devanmejia.services.messageSender;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Service
public abstract class EmailMessageSender<T> {
    private static Properties emailPropertiesConfig;
    private static Properties emailProperties;

    public EmailMessageSender() {
        try {
            emailPropertiesConfig = loadProperties("properties/emailConfiguration.properties");
            emailProperties = loadProperties("properties/email.properties");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public abstract void sendMessage(T emailSubject) throws IOException, MessagingException;

    protected Message createBasicMessage(String addresseeEmail) throws MessagingException {
        Message message = new MimeMessage(createSession());
        message.setFrom(new InternetAddress(emailProperties.getProperty("emailAddress")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(addresseeEmail));
        return message;
    }

    private Session createSession() {
        return Session.getDefaultInstance(emailPropertiesConfig, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getProperty("emailAddress"), emailProperties.getProperty("emailPassword"));
            }
        });
    }

    private Properties loadProperties(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        InputStream inStreamEmailPropertiesConfig = EmailMessageSender.class.getClassLoader().getResourceAsStream(propertiesPath);
        properties.load(inStreamEmailPropertiesConfig);
        return properties;
    }

}
