package devanmejia.models.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Column(name = "amount")
    private int amount;

}

