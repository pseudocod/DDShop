package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product p WHERE p.id IS NOT NULL ORDER BY p.id ASC LIMIT 3", nativeQuery = true)
    List<Product> findFirst3Products();

    List<Product> findProductByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
}
