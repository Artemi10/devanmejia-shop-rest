package devanmejia.transfer;

import devanmejia.models.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class OrderDTO {
    private int id;
    private String owner;
    private String orderStatus;

    public static OrderDTO form(Order order){
        return OrderDTO.builder().id(order.getId())
                .owner(order.getUser().getFirstName()+" "+order.getUser().getLastName())
                .orderStatus(order.getOrderStatus().name()).build();
    }

    public static List<OrderDTO> form(List<Order> orders){
        List<OrderDTO> result = new ArrayList<>();
        for(Order order: orders)
            result.add(form(order));
        return result;
    }
}
