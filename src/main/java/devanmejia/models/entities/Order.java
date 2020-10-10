package devanmejia.models.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import devanmejia.models.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="orders")
@ToString(exclude = {"cartProduct","user"} )
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_login", referencedColumnName = "login")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartProduct> cartProduct;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

}

