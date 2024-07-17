package dd.projects.demo.domain.dto.Order;

import dd.projects.demo.domain.dto.Address.AddressSummaryDto;
import dd.projects.demo.domain.dto.Cart.CartSummaryDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.Address;
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
public class OrderSummaryDto {
    private Long id;
    private UserSummaryDto user;
    private CartSummaryDto cart;
    private String paymentType;
    private BigDecimal totalPrice;
    private LocalDate orderDate;
    private AddressSummaryDto deliveryAddress;
    private AddressSummaryDto invoiceAddress;


}
