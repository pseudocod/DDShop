package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Order.OrderCreateRequestDto;
import dd.projects.demo.domain.dto.Order.OrderEditRequestDto;
import dd.projects.demo.domain.dto.Order.OrderResponseDto;
import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.Order;
import dd.projects.demo.domain.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PaymentTypeMapper.class, AddressMapper.class, CartMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderSummaryDto toOrderSummaryDto(Order order);

    Order toEntity(OrderSummaryDto orderSummaryDto);

    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderCreateRequestDto orderCreateRequestDto);

    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderEditRequestDto orderEditRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);

    @Named("toUserSummaryDto")
    default UserSummaryDto mapUserToUserSummaryDto(User user) {
        if (user == null) {
            return null;
        }
        return UserSummaryDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
