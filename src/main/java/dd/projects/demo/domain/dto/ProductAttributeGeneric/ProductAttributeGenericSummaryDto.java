package dd.projects.demo.domain.dto.ProductAttributeGeneric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeGenericSummaryDto {
    private Long id;
    private String name;
}
