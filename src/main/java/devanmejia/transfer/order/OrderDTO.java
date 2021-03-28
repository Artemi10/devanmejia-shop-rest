package devanmejia.transfer.order;

import devanmejia.models.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String owner;
    private String orderStatus;

    public static OrderDTO form(Order order){
        return OrderDTO.builder().id(order.getId())
                .owner(order.getShopUser().getFirstName() + " " + order.getShopUser().getLastName())
                .orderStatus(order.getOrderStatus().name()).build();
    }

    public static List<OrderDTO> form(List<Order> orders){
        List<OrderDTO> result = new ArrayList<>();
        for(Order order: orders)
            result.add(form(order));
        return result;
    }
}
