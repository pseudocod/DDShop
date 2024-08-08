package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.CartEntry.CartEntryCreateRequestDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntryEditDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntryResponseDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntrySummaryDto;
import dd.projects.demo.domain.entitiy.Cart;
import dd.projects.demo.domain.entitiy.CartEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(uses = {ProductMapper.class, CartMapper.class})
public interface CartEntryMapper {
    CartEntryMapper INSTANCE = Mappers.getMapper(CartEntryMapper.class);

    @Mapping(source = "productId", target = "product.id")
    CartEntry toEntity(CartEntryCreateRequestDto cartEntryCreateRequestDto);
    @Mapping(source = "cartEntryId", target = "id")
    CartEntry toEntity(CartEntryEditDto cartEntryEditDtoRequestDto);
    CartEntryResponseDto toCartEntryResponseDto(Cart cart);
    CartEntrySummaryDto toCartEntrySummaryDto(Cart cart);
}
