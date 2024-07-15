package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
