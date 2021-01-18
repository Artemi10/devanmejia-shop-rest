package devanmejia.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Tokens {
    private String accessToken;
    private String refreshToken;
}
