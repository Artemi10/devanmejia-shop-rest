package devanmejia.transfer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailOrderDTO {
    private Long id;
    private String email;
    private String orderStatus;
}
