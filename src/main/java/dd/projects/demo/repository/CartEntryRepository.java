package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {
}
