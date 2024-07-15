package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
