package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.streetLine = :streetLine AND a.city = :city AND a.postalCode = :postalCode AND a.county = :county AND a.country = :country")
    Optional<Address> findByDetails(
            @Param("streetLine") String streetLine,
            @Param("city") String city,
            @Param("postalCode") String postalCode,
            @Param("county") String county,
            @Param("country") String country
    );
}
