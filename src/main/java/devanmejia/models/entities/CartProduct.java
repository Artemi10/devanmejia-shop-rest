package devanmejia.models.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@Table(name = "cart_products")
@NoArgsConstructor
@JsonAutoDetect
public class CartProduct {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;
    @Column(name="amount")
    private int amount;
    @ManyToOne
    @JoinColumn(name= "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    private Order order;
}

