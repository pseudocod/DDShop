package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.ProductAttributeConcrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeConcreteRepository extends JpaRepository<ProductAttributeConcrete, Long> {
}
