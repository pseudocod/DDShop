package dd.projects.demo.domain.dto.Cart;

import dd.projects.demo.domain.dto.CartEntry.CartEntrySummaryDto;
import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long id;
    private BigDecimal totalPrice;
    private List<CartEntrySummaryDto> cartEntries;
}
