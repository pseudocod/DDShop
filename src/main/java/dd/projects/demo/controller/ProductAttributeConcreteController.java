package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteResponseDto;
import dd.projects.demo.domain.dto.ProductAttributeConcrete.ProductAttributeConcreteUpdateRequestDto;
import dd.projects.demo.service.ProductAttributeConcreteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-attributes-concrete")
public class ProductAttributeConcreteController {
    private final ProductAttributeConcreteService productAttributeConcreteService;

    public ProductAttributeConcreteController(ProductAttributeConcreteService productAttributeConcreteService) {
        this.productAttributeConcreteService = productAttributeConcreteService;
    }

    @GetMapping("/id")
    public ResponseEntity<ProductAttributeConcreteResponseDto> getProductAttributeConcreteById(@PathVariable Long id) {
        try {
            ProductAttributeConcreteResponseDto productAttributeConcreteResponseDto = productAttributeConcreteService.getProductAttributeConcreteResponseById(id);
            return new ResponseEntity<>(productAttributeConcreteResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductAttributeConcreteResponseDto>> getAllProductAttributesConcrete() {
        return new ResponseEntity<>(productAttributeConcreteService.getAllProductAttributeConcrete(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductAttributeConcreteResponseDto> createProductAttributeConcrete(@RequestBody ProductAttributeConcreteCreateRequestDto productAttributeConcreteCreateRequestDto) {
        try {
            ProductAttributeConcreteResponseDto productAttributeConcreteResponseDto = productAttributeConcreteService.createProductAttributeConcrete(productAttributeConcreteCreateRequestDto);
            return new ResponseEntity<>(productAttributeConcreteResponseDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ProductAttributeConcreteResponseDto> updateProductAttributeConcrete(@PathVariable Long id, @RequestBody ProductAttributeConcreteUpdateRequestDto productAttributeConcreteUpdateRequestDto) {
        try {
            ProductAttributeConcreteResponseDto productAttributeConcreteResponseDto = productAttributeConcreteService.updateProductAttributeConcrete(id, productAttributeConcreteUpdateRequestDto);
            return new ResponseEntity<>(productAttributeConcreteResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductAttributeConcrete(@PathVariable Long id) {
        try {
            productAttributeConcreteService.deleteProductAttributeConcrete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
