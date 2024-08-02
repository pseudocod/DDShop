package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product p WHERE p.id IS NOT NULL ORDER BY p.id ASC LIMIT 3", nativeQuery = true)
    List<Product> findFirst3Products();
}
