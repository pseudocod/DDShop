package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.AttributeValueConcrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueConcreteRepository extends JpaRepository<AttributeValueConcrete, Long> {
}
