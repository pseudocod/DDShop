package dd.projects.demo.domain.dto.Cart;

import dd.projects.demo.domain.dto.User.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryDto {
    private Long id;
    private UserSummaryDto user;
    private BigDecimal totalPrice;
}
