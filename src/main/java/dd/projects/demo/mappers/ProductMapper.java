package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Product.ProductCreateRequestDto;
import dd.projects.demo.domain.dto.Product.ProductEditRequestDto;
import dd.projects.demo.domain.dto.Product.ProductResponseDto;
import dd.projects.demo.domain.dto.Product.ProductSummaryDto;
import dd.projects.demo.domain.dto.ProductAttribute.ProductAttributeResponseDto;
import dd.projects.demo.domain.entitiy.Product;
import dd.projects.demo.domain.entitiy.ProductAttributeConcrete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CategoryMapper.class, CartEntryMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "productAttributes", ignore = true)
    Product toEntity(ProductCreateRequestDto dto);
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "productAttributes", ignore = true)
    Product toEntity(ProductEditRequestDto dto);

    ProductSummaryDto toSummaryDto(Product product);

    @Mapping(target = "attributes", source = "productAttributes")
    ProductResponseDto toResponseDto(Product product);

    default List<ProductAttributeResponseDto> mapProductAttributes(List<ProductAttributeConcrete> productAttributes) {
        return productAttributes.stream()
                .map(attr -> ProductAttributeResponseDto.builder()
                        .attributeName(attr.getAttributeValueConcrete().getAttribute().getName())
                        .attributeValue(attr.getAttributeValueConcrete().getValue().getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
