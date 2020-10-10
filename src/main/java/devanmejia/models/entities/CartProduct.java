package devanmejia.models.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Data
@Table(name="cart_products")
@NoArgsConstructor
@JsonAutoDetect
public class CartProduct {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Integer id;
    @Column(name="amount")
    private int amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_name", referencedColumnName = "product_name")
    private Product product;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    private Order order;

}

