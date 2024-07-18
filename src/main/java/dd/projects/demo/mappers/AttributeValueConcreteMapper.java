package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteEditDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteResponseDto;
import dd.projects.demo.domain.entitiy.AttributeValueConcrete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductAttributeGenericMapper.class, AttributeValueGenericMapper.class })
public interface AttributeValueConcreteMapper {
    AttributeValueConcreteMapper INSTANCE = Mappers.getMapper(AttributeValueConcreteMapper.class);

    @Mapping(source = "attributeId", target = "attribute.id")
    @Mapping(source = "valueId", target = "value.id")
    AttributeValueConcrete toEntity(AttributeValueConcreteCreateRequestDto dto);

    @Mapping(source = "attributeId", target = "attribute.id")
    @Mapping(source = "valueId", target = "value.id")
    AttributeValueConcrete toEntity(AttributeValueConcreteEditDto dto);
    @Mapping(target = "productAttributeGenericSummaryDto", source = "attribute")
    @Mapping(target = "attributeValueGenericResponseDto", source = "value")
    AttributeValueConcreteResponseDto toResponseDto(AttributeValueConcrete entity);

}
