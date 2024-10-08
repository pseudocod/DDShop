package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Category.CategoryCreateRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryEditRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryWithProductsResponseDto;
import dd.projects.demo.domain.dto.Category.CategoryWithoutProductsResponseDto;
import dd.projects.demo.domain.entitiy.Category;
import dd.projects.demo.domain.entitiy.Product;
import dd.projects.demo.mappers.CategoryMapper;
import dd.projects.demo.repository.CategoryRepository;
import dd.projects.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    public CategoryService(CategoryRepository categoryRepository,
                           ProductService productService,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public CategoryWithoutProductsResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryMapper.INSTANCE.toResponseDto(category);
    }

    public CategoryWithProductsResponseDto getCategoryWithProductsById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryMapper.INSTANCE.toWithProductsResponseDto(category);
    }

    public List<CategoryWithoutProductsResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<CategoryWithProductsResponseDto> getAllCategoriesWithProducts() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper.INSTANCE::toWithProductsResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public CategoryWithoutProductsResponseDto createCategory(CategoryCreateRequestDto dto) {
        Category category = CategoryMapper.INSTANCE.toEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toResponseDto(savedCategory);
    }

    @Transactional
    public CategoryWithoutProductsResponseDto editCategory(Long id, CategoryEditRequestDto dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.INSTANCE.toResponseDto(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = category.getProducts();
        for (Product product : products) {
            product.setCategory(null);
            productRepository.save(product);
        }

        categoryRepository.delete(category);
    }
}