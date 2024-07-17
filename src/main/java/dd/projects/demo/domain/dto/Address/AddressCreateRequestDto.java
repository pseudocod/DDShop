package dd.projects.demo.domain.dto.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateRequestDto {
    private String streetLine;
    private String postalCode;
    private String city;
    private String county;
    private String country;
}
