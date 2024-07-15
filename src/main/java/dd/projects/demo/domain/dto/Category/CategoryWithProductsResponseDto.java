package dd.projects.demo.domain.dto.Category;

import dd.projects.demo.domain.dto.Product.ProductSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryWithProductsResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductSummaryDto> products;
}
