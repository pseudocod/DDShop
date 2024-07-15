package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
