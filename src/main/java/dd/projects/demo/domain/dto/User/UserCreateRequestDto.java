package dd.projects.demo.domain.dto.User;

import dd.projects.demo.domain.dto.Address.AddressSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
