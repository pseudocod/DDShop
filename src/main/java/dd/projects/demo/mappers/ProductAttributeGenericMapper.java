package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericCreateDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericEditDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericResponseDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericSummaryDto;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAttributeGenericMapper {
    ProductAttributeGenericMapper INSTANCE = Mappers.getMapper(ProductAttributeGenericMapper.class);
    ProductAttributeGeneric toEntity(ProductAttributeGenericSummaryDto productAttributeGenericSummaryDto);
    ProductAttributeGenericSummaryDto toSummaryDto(ProductAttributeGeneric productAttributeGeneric);
    ProductAttributeGeneric toEntity(ProductAttributeGenericEditDto productAttributeGenericEditDto);
    ProductAttributeGeneric toEntity(ProductAttributeGenericCreateDto productAttributeGenericCreateDto);
    ProductAttributeGenericResponseDto toResponseDto(ProductAttributeGeneric productAttributeGeneric);
}
