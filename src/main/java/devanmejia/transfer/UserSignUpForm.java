package devanmejia.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpForm {
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
