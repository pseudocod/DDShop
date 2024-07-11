package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
