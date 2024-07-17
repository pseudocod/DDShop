package dd.projects.demo.domain.dto.CartEntry;

import dd.projects.demo.domain.dto.Cart.CartSummaryDto;
import dd.projects.demo.domain.dto.Product.ProductSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartEntrySummaryDto {
    private Long id;
    private CartSummaryDto cart;
    private ProductSummaryDto product;
    private Integer quantity;
    private BigDecimal pricePerPiece;
    private BigDecimal totalPriceEntry;
}
