package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericDto;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAttributeGenericMapper {
    ProductAttributeGenericMapper INSTANCE = Mappers.getMapper(ProductAttributeGenericMapper.class);
    ProductAttributeGeneric toEntity(ProductAttributeGenericDto productAttributeGenericDto);
    ProductAttributeGenericDto toDto(ProductAttributeGeneric productAttributeGenericDto);
}
