package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeGenericRepository extends JpaRepository<ProductAttributeGeneric, Long> {
}
