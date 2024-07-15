package dd.projects.demo.domain.dto.ProductAttributeConcrete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeConcreteUpdateRequestDto {
    private Long id; // ID is needed for the update
    private Long productId;
    private Long attributeId;
}
