package dd.projects.demo.service;

import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteResponseDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteUpdateRequestDto;
import dd.projects.demo.domain.entitiy.Product;
import dd.projects.demo.domain.entitiy.ProductAttributeConcrete;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import dd.projects.demo.mappers.ProductAttributeConcreteMapper;
import dd.projects.demo.repository.ProductAttributeConcreteRepository;
import dd.projects.demo.repository.ProductAttributeGenericRepository;
import dd.projects.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeConcreteService {
    private final ProductAttributeConcreteRepository productAttributeConcreteRepository;
    private final ProductRepository productRepository;
    private final ProductAttributeGenericRepository productAttributeGenericRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductAttributeConcreteService.class);


    public ProductAttributeConcreteService(ProductAttributeConcreteRepository productAttributeConcreteRepository,
                                           ProductRepository productRepository,
                                           ProductAttributeGenericRepository productAttributeGenericRepository) {
        this.productAttributeConcreteRepository = productAttributeConcreteRepository;
        this.productRepository = productRepository;
        this.productAttributeGenericRepository = productAttributeGenericRepository;
    }

    public ProductAttributeConcreteResponseDto getProductAttributeConcreteResponseById(Long id) {
        ProductAttributeConcrete productAttributeConcrete = productAttributeConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute concrete not found"));
        return ProductAttributeConcreteMapper.INSTANCE.toResponse(productAttributeConcrete);
    }

    public List<ProductAttributeConcreteResponseDto> getAllProductAttributeConcrete() {
        return productAttributeConcreteRepository.findAll().stream()
                .map(ProductAttributeConcreteMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    public ProductAttributeConcreteResponseDto createProductAttributeConcrete(ProductAttributeConcreteCreateRequestDto productAttributeConcreteCreateRequestDto) {
        Product product = productRepository.findById(productAttributeConcreteCreateRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductAttributeGeneric productAttributeGeneric = productAttributeGenericRepository.findById(productAttributeConcreteCreateRequestDto.getAttributeId())
                .orElseThrow(() -> new EntityNotFoundException("Product attribute generic not found"));

        ProductAttributeConcrete productAttributeConcrete = ProductAttributeConcreteMapper.INSTANCE.toEntity(productAttributeConcreteCreateRequestDto);


        productAttributeConcrete.setProduct(product);
        productAttributeConcrete.setAttribute(productAttributeGeneric);

        ProductAttributeConcrete savedProductAttributeConcrete = productAttributeConcreteRepository.save(productAttributeConcrete);

        return ProductAttributeConcreteMapper.INSTANCE.toResponse(savedProductAttributeConcrete);
    }
    @Transactional
    public ProductAttributeConcreteResponseDto updateProductAttributeConcrete(Long id, ProductAttributeConcreteUpdateRequestDto productAttributeConcreteUpdateRequestDto) {
        Product product = productRepository.findById(productAttributeConcreteUpdateRequestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductAttributeGeneric productAttributeGeneric = productAttributeGenericRepository.findById(productAttributeConcreteUpdateRequestDto.getAttributeId())
                .orElseThrow(() -> new EntityNotFoundException("Product attribute generic not found"));

        ProductAttributeConcrete productAttributeConcrete = productAttributeConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute concrete not found"));

        productAttributeConcrete.setId(id);
        productAttributeConcrete.setProduct(product);
        productAttributeConcrete.setAttribute(productAttributeGeneric);

        ProductAttributeConcrete savedProductAttributeConcrete = productAttributeConcreteRepository.save(productAttributeConcrete);

        return ProductAttributeConcreteMapper.INSTANCE.toResponse(savedProductAttributeConcrete);
    }

    public void deleteProductAttributeConcrete(Long id) {
        ProductAttributeConcrete productAttributeConcrete = productAttributeConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute concrete not found"));
        productAttributeConcreteRepository.delete(productAttributeConcrete);
    }
}

