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
    @Column(name="product_name")
    @Id
    private String name;
    @Column(name="product_price")
    private int price;
    @Column(name="product_description")
    private String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pictures_picture_name", referencedColumnName = "picture_name")
    private Picture picture;
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;


}
