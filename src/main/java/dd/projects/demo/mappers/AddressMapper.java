package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Address.AddressCreateRequestDto;
import dd.projects.demo.domain.dto.Address.AddressEditRequestDto;
import dd.projects.demo.domain.dto.Address.AddressResponseDto;
import dd.projects.demo.domain.dto.Address.AddressSummaryDto;
import dd.projects.demo.domain.entitiy.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OrderMapper.class, UserMapper.class} )
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    Address toEntity(AddressCreateRequestDto addressCreateRequestDto);
    Address toEntity(AddressEditRequestDto addressEditRequestDto);
    AddressResponseDto toAddressResponseDto(Address address);
    AddressSummaryDto toAddressSummaryDto(Address address);
}
