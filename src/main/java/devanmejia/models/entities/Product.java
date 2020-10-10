package devanmejia.models.entities;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name="products")
@NoArgsConstructor
@JsonAutoDetect
public class Product {
    @Id
    @Column(name="product_name")
    private String name;
    @Column(name="product_price")
    private int price;
    @Column(name="product_description")
    private String description;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "pictures_picture_name", referencedColumnName = "picture_name")
    private Picture picture;


}
