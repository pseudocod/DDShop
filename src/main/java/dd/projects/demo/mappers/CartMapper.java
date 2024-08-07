package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Cart.CartCreateRequestDto;
import dd.projects.demo.domain.dto.Cart.CartEditRequestDto;
import dd.projects.demo.domain.dto.Cart.CartResponseDto;
import dd.projects.demo.domain.dto.Cart.CartSummaryDto;
import dd.projects.demo.domain.entitiy.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CartEntryMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart toEntity(CartCreateRequestDto cartCreateRequestDto);

    Cart toEntity(CartEditRequestDto cartEditRequestDto);

    CartResponseDto toCartResponseDto(Cart cart);

    CartSummaryDto toCartSummaryDto(Cart cart);
}
