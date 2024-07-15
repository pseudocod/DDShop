package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
