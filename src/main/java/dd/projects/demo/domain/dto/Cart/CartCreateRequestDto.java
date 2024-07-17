package dd.projects.demo.domain.dto.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartCreateRequestDto {
    private Long userId;
    private BigDecimal totalPrice;
}
