package dd.projects.demo.domain.dto.Product;

import dd.projects.demo.domain.dto.ProductAttribute.ProductAttributeCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEditRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private Long categoryId;
    private List<ProductAttributeCreateRequestDto> attributes;
}
