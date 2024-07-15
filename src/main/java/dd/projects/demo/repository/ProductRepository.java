package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
