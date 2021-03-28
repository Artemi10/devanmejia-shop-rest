package devanmejia.models.entities;

import devanmejia.models.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "user_id", referencedColumnName = "id")
    private ShopUser shopUser;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartProduct> cartProducts;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

}

