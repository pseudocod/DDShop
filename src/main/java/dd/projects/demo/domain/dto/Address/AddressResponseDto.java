package dd.projects.demo.domain.dto.Address;

import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
    private String streetLine;
    private String postalCode;
    private String city;
    private String county;
    private String country;
    private List<UserSummaryDto>  defaultDeliveryUsers;
    private List<UserSummaryDto>  billingAddressUsers;
    private List<OrderSummaryDto> deliveryAddresses;
    private List<OrderSummaryDto> invoiceAddresses;
}
