package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAttributeGenericRepository extends JpaRepository<ProductAttributeGeneric, Long> {
    Optional<ProductAttributeGeneric> findByName(String attribute);
}
