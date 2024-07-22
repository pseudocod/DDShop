package dd.projects.demo.domain.dto.Product;

import dd.projects.demo.domain.dto.CartEntry.CartEntrySummaryDto;
import dd.projects.demo.domain.dto.Category.CategoryWithoutProductsResponseDto;
import dd.projects.demo.domain.dto.ProductAttribute.ProductAttributeDto;
import dd.projects.demo.domain.dto.ProductAttribute.ProductAttributeResponseDto;
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
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private LocalDate addedDate;
    private CategoryWithoutProductsResponseDto category;
    private List<ProductAttributeResponseDto> attributes;
}
