package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteResponseDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteUpdateRequestDto;
import dd.projects.demo.domain.entitiy.ProductAttributeConcrete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class, ProductAttributeGenericMapper.class})
public interface ProductAttributeConcreteMapper {
    ProductAttributeConcreteMapper INSTANCE = Mappers.getMapper(ProductAttributeConcreteMapper.class);

    // Mapping from Create DTO to Entity
    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "attributeId", target = "attribute.id")
    ProductAttributeConcrete toEntity(ProductAttributeConcreteCreateRequestDto dto);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "attributeId", target = "attribute.id")
    ProductAttributeConcrete toEntity(ProductAttributeConcreteUpdateRequestDto dto);

    ProductAttributeConcrete toEntity(ProductAttributeConcreteResponseDto dto);

    ProductAttributeConcreteResponseDto toResponse(ProductAttributeConcrete entity);


}
