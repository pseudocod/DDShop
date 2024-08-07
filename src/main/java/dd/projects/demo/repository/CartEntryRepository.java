package dd.projects.demo.repository;

import dd.projects.demo.domain.entitiy.Cart;
import dd.projects.demo.domain.entitiy.CartEntry;
import dd.projects.demo.domain.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {
    CartEntry findCartEntryByProductAndCart(Product product, Cart cart);
}
