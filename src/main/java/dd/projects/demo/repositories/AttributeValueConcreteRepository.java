package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.AttributeValueConcrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueConcreteRepository extends JpaRepository<AttributeValueConcrete, Long> {
}
