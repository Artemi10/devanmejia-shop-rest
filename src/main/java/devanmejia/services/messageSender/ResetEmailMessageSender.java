package devanmejia.services.messageSender;

import devanmejia.models.entities.User;
import devanmejia.services.secretCode.SecretCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;

@Service
public class ResetEmailMessageSender extends EmailMessageSender<User>{
    @Autowired
    @Qualifier("resetCodeService")
    private SecretCodeService resetCodeService;

    @Override
    public void sendMessage(User emailSubject) throws IOException, MessagingException {
        Message message = createResetPasswordMessage(emailSubject);
        Transport.send(message);
    }
    private Message createResetPasswordMessage(User user) throws MessagingException {
        String code = resetCodeService.generateNewCode(user);
        Message message = createBasicMessage(user.getEmail());
        message.setSubject("Reset password");
        message.setText("Reset password code: " + code);
        return message;
    }
}
