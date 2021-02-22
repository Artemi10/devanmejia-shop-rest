package devanmejia.services.messageSender;

import devanmejia.models.entities.User;
import devanmejia.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;

@Service
public class AuthEmailMessageSender extends EmailMessageSender<User>{
    @Autowired
    private UserService userService;

    @Override
    public void sendMessage(User emailSubject) throws MessagingException {
        Message message = createAuthMessage(emailSubject);
        Transport.send(message);
    }

    private Message createAuthMessage(User user) throws MessagingException {
        String code = userService.generateNewLogInCode(user);
        Message message = createBasicMessage(user.getEmail());
        message.setSubject("Confirm password");
        message.setText("Your code: " + code);
        return message;
    }
}
