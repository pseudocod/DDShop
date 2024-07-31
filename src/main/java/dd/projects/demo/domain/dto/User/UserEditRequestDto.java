package dd.projects.demo.domain.dto.User;

import dd.projects.demo.domain.dto.Address.AddressEditRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private AddressEditRequestDto defaultDeliveryAddress;
    private AddressEditRequestDto defaultBillingAddress;
}
