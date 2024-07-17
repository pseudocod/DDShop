package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Order.OrderCreateRequestDto;
import dd.projects.demo.domain.dto.Order.OrderEditRequestDto;
import dd.projects.demo.domain.dto.Order.OrderResponseDto;
import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import dd.projects.demo.domain.entitiy.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PaymentTypeMapper.class, AddressMapper.class, CartMapper.class, UserMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderSummaryDto toOrderSummaryDto(Order order);
    Order toEntity(OrderSummaryDto orderSummaryDto);
    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deliveryAddressId", target = "deliveryAddress.id")
    @Mapping(source = "invoiceAddressId", target = "invoiceAddress.id")
    Order toEntity(OrderCreateRequestDto orderCreateRequestDto);
    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deliveryAddressId", target = "deliveryAddress.id")
    @Mapping(source = "invoiceAddressId", target = "invoiceAddress.id")
    Order toEntity(OrderEditRequestDto orderEditRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);

}
