package dd.projects.demo.domain.dto.ProductAttribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeResponseDto {
    private String attributeName;
    private String attributeValue;
}
