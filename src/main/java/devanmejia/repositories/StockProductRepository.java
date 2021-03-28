package devanmejia.repositories;

import devanmejia.models.entities.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Long> {
    Optional<StockProduct> getByProductName(String productName);
    void deleteByProductName(String productName);
    @Query(value = "SELECT * FROM stock as stock_product " +
            "join products p on p.id = stock_product.product_id order by p.product_name",
    nativeQuery = true)
    List<StockProduct> findAllByOrderByProductName();

}
