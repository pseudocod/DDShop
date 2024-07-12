package dd.projects.demo.IntegrationTests;

import dd.projects.demo.entitiy.ProductAttributeGeneric;
import dd.projects.demo.repositories.ProductAttributeGenericRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductAttributeGenericRepositoryTest {

    @Autowired
    private ProductAttributeGenericRepository repository;

    @Test
    public void testInsertProductAttributeGeneric() {
        ProductAttributeGeneric attribute = new ProductAttributeGeneric();
        attribute.setName("Color");

        attribute = repository.save(attribute);

        assertThat(attribute.getId()).isNotNull();
        assertThat(attribute.getName()).isEqualTo("Color");

        ProductAttributeGeneric foundAttribute = repository.findById(attribute.getId()).orElse(null);
        assertThat(foundAttribute).isNotNull();
        assertThat(foundAttribute.getName()).isEqualTo("Color");
    }
}

