package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Product.ProductCreateRequestDto;
import dd.projects.demo.domain.dto.Product.ProductEditRequestDto;
import dd.projects.demo.domain.dto.Product.ProductResponseDto;
import dd.projects.demo.domain.dto.Product.ProductSummaryDto;
import dd.projects.demo.domain.entitiy.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CategoryMapper.class, ProductAttributeConcreteMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toResponseDto(Product product);
    ProductSummaryDto toSummaryDto(Product product);
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductCreateRequestDto dto);
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductEditRequestDto dto);

}
