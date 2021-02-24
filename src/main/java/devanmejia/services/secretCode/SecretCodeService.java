package devanmejia.services.secretCode;

import devanmejia.models.entities.User;
import devanmejia.transfer.UserCodeDTO;
import org.springframework.stereotype.Service;

@Service
public interface SecretCodeService{
    String generateNewCode(User user);
    User checkUserCode(UserCodeDTO userCodeDTO);
}
