package dd.projects.demo.domain.dto.AttributeValueGeneric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeValueGenericEditDto {
    private Long id;
    private String value;
}
