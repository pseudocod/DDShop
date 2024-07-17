package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericEditDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericResponseDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericSummaryDto;
import dd.projects.demo.domain.entitiy.AttributeValueGeneric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AttributeValueGenericMapper {
    AttributeValueGenericMapper INSTANCE = Mappers.getMapper((AttributeValueGenericMapper.class));
    AttributeValueGeneric toEntity(AttributeValueGenericEditDto attributeValueGenericEditDto);
    AttributeValueGeneric toEntity(AttributeValueGenericCreateRequestDto attributeValueGenericCreateRequestDto);
    AttributeValueGenericResponseDto toDto(AttributeValueGeneric attributeValueGeneric);
    AttributeValueGenericSummaryDto toSummaryDto(AttributeValueGeneric attributeValueGeneric);
}
