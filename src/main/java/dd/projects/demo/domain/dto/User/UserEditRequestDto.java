package dd.projects.demo.domain.dto.User;

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
    private String email;
    private String phoneNumber;
    private String password;
    private Long defaultDeliveryAddressId;
    private Long defaultBillingAddressId;
}
