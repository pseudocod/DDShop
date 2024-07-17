package dd.projects.demo.domain.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private LocalDate addedDate;
    private Long categoryId;
}
