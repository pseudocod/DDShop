package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.Product.ProductCreateRequestDto;
import dd.projects.demo.domain.dto.Product.ProductEditRequestDto;
import dd.projects.demo.domain.dto.Product.ProductResponseDto;
import dd.projects.demo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        try {
            ProductResponseDto productResponseDto = productService.getProductById(id);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        try {
            ProductResponseDto productResponseDto = productService.createProduct(productCreateRequestDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductEditRequestDto productEditRequestDto) {
        try {
            ProductResponseDto productResponseDto = productService.editProduct(id, productEditRequestDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
