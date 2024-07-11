package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
