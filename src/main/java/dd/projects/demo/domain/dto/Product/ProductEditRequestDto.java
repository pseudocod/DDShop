package dd.projects.demo.domain.dto.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductEditRequestDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private LocalDate addedDate;
    private Long categoryId;
}
