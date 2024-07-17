package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Order.OrderSummaryDto;
import dd.projects.demo.domain.entitiy.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PaymentTypeMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderSummaryDto toOrderSummaryDto(Order order);

    Order toOrder(OrderSummaryDto orderSummaryDto);
}
