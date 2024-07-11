package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {
}
