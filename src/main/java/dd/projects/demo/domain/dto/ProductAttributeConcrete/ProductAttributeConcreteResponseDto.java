package dd.projects.demo.domain.dto.ProductAttributeConcrete;

import dd.projects.demo.domain.dto.Product.ProductSummaryDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeConcreteResponseDto {
    private Long id;
    private ProductSummaryDto product;
    private ProductAttributeGenericDto attribute;
}
