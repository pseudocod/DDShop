package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product p WHERE p.id IS NOT NULL AND p.availablequantity > 0 ORDER BY p.addeddate DESC LIMIT 3", nativeQuery = true)
    List<Product> findFirst3Products();

    List<Product> findProductByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "WITH distinct_origins AS ( " +
            "    SELECT DISTINCT avg.value AS origin_value " +
            "    FROM attribute_value_generic avg " +
            "    JOIN attribute_value_concrete avc ON avg.id = avc.value_id " +
            "    JOIN product_attribute_generic pag ON avc.attribute_id = pag.id " +
            "    WHERE pag.name = 'Origin' " +
            "      AND avg.value IN ('Brazil', 'Rwanda', 'Ethiopia', 'Colombia', 'Peru') " +
            ") " +
            "SELECT p.* " +
            "FROM product p " +
            "JOIN product_attribute_concrete pac ON p.id = pac.product_id " +
            "JOIN attribute_value_concrete avc ON pac.attribute_id = avc.id " +
            "JOIN attribute_value_generic avg ON avc.value_id = avg.id " +
            "JOIN distinct_origins distinct_origin ON avg.value = distinct_origin.origin_value " +
            "WHERE p.availablequantity > 0 " +
            "ORDER BY p.id", nativeQuery = true)
    List<Product> findOneProductPerOrigin();
}

