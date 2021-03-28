package devanmejia.models.entities;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import devanmejia.models.enums.ProductType;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAutoDetect
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="product_name")
    private String name;
    @Column(name="product_price")
    private int price;
    @Column(name="product_description")
    private String description;
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;
    @Transient
    private String imageLink;
}
