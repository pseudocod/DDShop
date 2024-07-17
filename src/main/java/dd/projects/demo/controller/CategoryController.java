package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.Category.CategoryCreateRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryEditRequestDto;
import dd.projects.demo.domain.dto.Category.CategoryWithProductsResponseDto;
import dd.projects.demo.domain.dto.Category.CategoryWithoutProductsResponseDto;
import dd.projects.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryWithoutProductsResponseDto> getCategoryById(@PathVariable Long id) {
        try{
            CategoryWithoutProductsResponseDto category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}/with-products")
    public ResponseEntity<CategoryWithProductsResponseDto> getCategoryWithProductsById(@PathVariable Long id) {
        try {
            CategoryWithProductsResponseDto category = categoryService.getCategoryWithProductsById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryWithoutProductsResponseDto>> getAllCategories() {
        List<CategoryWithoutProductsResponseDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/with-products")
    public ResponseEntity<List<CategoryWithProductsResponseDto>> getAllCategoriesWithProducts() {
        List<CategoryWithProductsResponseDto> categories = categoryService.getAllCategoriesWithProducts();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryWithoutProductsResponseDto> createCategory(@RequestBody CategoryCreateRequestDto dto) {
        CategoryWithoutProductsResponseDto createdCategory = categoryService.createCategory(dto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryWithoutProductsResponseDto> editCategory(@PathVariable Long id, @RequestBody CategoryEditRequestDto dto) {
        return new ResponseEntity<>(categoryService.editCategory(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
