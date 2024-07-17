package dd.projects.demo.domain.dto.AttributeValueConcrete;

import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericSummaryDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValueConcreteResponseDto {
    private AttributeValueGenericSummaryDto attributeValueGenericResponseDto;
    private ProductAttributeGenericSummaryDto productAttributeGenericSummaryDto;
}
