package devanmejia.models.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name="stock")
@NoArgsConstructor
@AllArgsConstructor
public class StockProduct {
    @Id
    @Column(name = "product_name")
    private String productName;
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "product_name", referencedColumnName = "product_name")
    private Product product;
    @Column(name = "amount")
    private int amount;
}

