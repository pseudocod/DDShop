package dd.projects.demo.service;

import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericCreateDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericEditDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericResponseDto;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import dd.projects.demo.mappers.ProductAttributeGenericMapper;
import dd.projects.demo.repository.ProductAttributeGenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeGenericService {
    private final ProductAttributeGenericRepository productAttributeGenericRepository;
    public ProductAttributeGenericService(ProductAttributeGenericRepository productAttributeGenericRepository) {
        this.productAttributeGenericRepository = productAttributeGenericRepository;
    }

    public ProductAttributeGenericResponseDto getProductAttributeGenericById(Long id) {
        ProductAttributeGeneric productAttributeGeneric = productAttributeGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product attribute not found"));
        return ProductAttributeGenericMapper.INSTANCE.toResponseDto(productAttributeGeneric);
    }

    public List<ProductAttributeGenericResponseDto> getAllProductAttributeGeneric() {
        return productAttributeGenericRepository.findAll().stream()
                .map(ProductAttributeGenericMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public ProductAttributeGenericResponseDto createProductAttributeGeneric(ProductAttributeGenericCreateDto productAttributeGenericCreateDto) {
        ProductAttributeGeneric productAttributeGeneric = ProductAttributeGenericMapper.INSTANCE.toEntity(productAttributeGenericCreateDto);
        ProductAttributeGeneric savedProductAttributeGeneric = productAttributeGenericRepository.save(productAttributeGeneric);
        return ProductAttributeGenericMapper.INSTANCE.toResponseDto(savedProductAttributeGeneric);
    }

    @Transactional
    public ProductAttributeGenericResponseDto editProductAttributeGeneric(Long id, ProductAttributeGenericEditDto productAttributeGenericEditDto) {

        ProductAttributeGeneric existingProductAttributeGeneric = productAttributeGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        existingProductAttributeGeneric.setId(id);
        existingProductAttributeGeneric.setName(productAttributeGenericEditDto.getName());

        ProductAttributeGeneric updatedProductAttributeGeneric = productAttributeGenericRepository.save(existingProductAttributeGeneric);

        return ProductAttributeGenericMapper.INSTANCE.toResponseDto(updatedProductAttributeGeneric);
    }

    public void deleteProductAttributeGeneric(Long id) {
        ProductAttributeGeneric productAttributeGeneric = productAttributeGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        productAttributeGenericRepository.delete(productAttributeGeneric);
    }
}
