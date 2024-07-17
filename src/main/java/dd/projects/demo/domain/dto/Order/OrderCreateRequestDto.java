package dd.projects.demo.domain.dto.Order;

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
public class OrderCreateRequestDto {
    private Long userId;
    private Long cartId;
    private PaymentType paymentType;
    private BigDecimal totalPrice;
    private LocalDate orderDate;
    private Long deliveryAddressId;
    private Long invoiceAddressId;
}
