package dd.projects.demo.domain.dto.Order;

import dd.projects.demo.domain.dto.Address.AddressSummaryDto;
import dd.projects.demo.domain.dto.Cart.CartSummaryDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.Address;
import dd.projects.demo.domain.entitiy.PaymentType;
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
public class OrderResponseDto {
    private Long id;
    private UserSummaryDto user;
    private CartSummaryDto cart;
    private PaymentType paymentType;
    private BigDecimal totalPrice;
    private LocalDate orderDate;
    private AddressSummaryDto deliveryAddress;
    private AddressSummaryDto invoiceAddress;
}
