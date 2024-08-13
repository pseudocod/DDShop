package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Order;
import dd.projects.demo.domain.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
