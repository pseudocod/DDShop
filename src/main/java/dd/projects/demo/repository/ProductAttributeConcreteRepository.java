package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.ProductAttributeConcrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeConcreteRepository extends JpaRepository<ProductAttributeConcrete, Long> {
}
