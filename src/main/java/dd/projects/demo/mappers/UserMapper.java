package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.User.UserCreateRequestDto;
import dd.projects.demo.domain.dto.User.UserEditRequestDto;
import dd.projects.demo.domain.dto.User.UserResponseDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class, OrderMapper.class, CartMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserSummaryDto toUserSummaryDto(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateRequestDto dto);
    User toEntity(UserSummaryDto dto);
    @Mapping(source = "defaultDeliveryAddressId", target = "defaultDeliveryAddress.id")
    @Mapping(source = "defaultBillingAddressId", target = "defaultBillingAddress.id")
    User toEntity(UserEditRequestDto dto);
    UserResponseDto toUserResponseDto(User user);
}
