package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.AttributeValueGeneric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeValueGenericRepository extends JpaRepository<AttributeValueGeneric, Long> {
    Optional<AttributeValueGeneric> findByValue(String value);
}
