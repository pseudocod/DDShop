package dd.projects.demo.repositories;

import dd.projects.demo.entitiy.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
