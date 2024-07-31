package dd.projects.demo.domain.dto.User;

import dd.projects.demo.domain.dto.Address.AddressSummaryDto;
import dd.projects.demo.domain.dto.Cart.CartSummaryDto;
import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressSummaryDto defaultDeliveryAddress;
    private AddressSummaryDto defaultBillingAddress;
}
