package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Product.ProductCreateRequestDto;
import dd.projects.demo.domain.dto.Product.ProductEditRequestDto;
import dd.projects.demo.domain.dto.Product.ProductResponseDto;
import dd.projects.demo.domain.entitiy.Category;
import dd.projects.demo.domain.entitiy.Product;
import dd.projects.demo.mappers.ProductMapper;
import dd.projects.demo.repository.CategoryRepository;
import dd.projects.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product"));
        return ProductMapper.INSTANCE.toResponseDto(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Category category = categoryRepository.findById(productCreateRequestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id " + productCreateRequestDto.getCategoryId()));

        Product product = ProductMapper.INSTANCE.toEntity(productCreateRequestDto);

        product.setCategory(category);

        Product savedProducts = productRepository.save(product);

        return ProductMapper.INSTANCE.toResponseDto(savedProducts);
    }
    @Transactional
    public ProductResponseDto editProduct(Long id, ProductEditRequestDto productEditRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id " + id));
        Category category = categoryRepository.findById(productEditRequestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id " + productEditRequestDto.getCategoryId()));

        product.setName(productEditRequestDto.getName());
        product.setDescription(productEditRequestDto.getDescription());
        product.setPrice(productEditRequestDto.getPrice());
        product.setAvailableQuantity(productEditRequestDto.getAvailableQuantity());
        product.setAddedDate(productEditRequestDto.getAddedDate());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.INSTANCE.toResponseDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id " + id));
        productRepository.delete(product);
    }
}
