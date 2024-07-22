package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.AttributeValueConcrete;
import dd.projects.demo.domain.entitiy.AttributeValueGeneric;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttributeValueConcreteRepository extends JpaRepository<AttributeValueConcrete, Long> {
    List<AttributeValueConcrete> findByAttributeAndValue(ProductAttributeGeneric attributeGeneric, AttributeValueGeneric value);
}
