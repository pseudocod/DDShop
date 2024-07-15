package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.Category.CategoryCreateRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryEditRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryWithoutProductsResponseDto;
import dd.projects.demo.domain.dto.Category.CategoryWithProductsResponseDto;
import dd.projects.demo.domain.entitiy.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductMapper.class)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryWithoutProductsResponseDto toResponseDto(Category category);
    CategoryWithProductsResponseDto toWithProductsResponseDto(Category category);
    Category toEntity(CategoryCreateRequestDto dto);
    Category toEntity(CategoryEditRequestDto dto);


}
