package dd.projects.demo.domain.dto.CartEntry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartEntryCreateRequestDto {
    private Long productId;
    private Integer quantity;
}
