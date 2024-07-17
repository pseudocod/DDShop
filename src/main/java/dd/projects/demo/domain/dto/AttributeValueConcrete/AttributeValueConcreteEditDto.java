package dd.projects.demo.domain.dto.AttributeValueConcrete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValueConcreteEditDto {
    private Long attributeId;
    private Long valueId;
}
