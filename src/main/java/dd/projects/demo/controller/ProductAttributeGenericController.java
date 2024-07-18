package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericCreateDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericEditDto;
import dd.projects.demo.domain.dto.ProductAttributeGeneric.ProductAttributeGenericResponseDto;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import dd.projects.demo.service.ProductAttributeGenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-attribute-generic")
public class ProductAttributeGenericController {
    private final ProductAttributeGenericService productAttributeGenericService;

    public ProductAttributeGenericController(ProductAttributeGenericService productAttributeGenericService) {
        this.productAttributeGenericService = productAttributeGenericService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeGenericResponseDto> getProductAttributeGenericById(@PathVariable Long id) {
        try {
            ProductAttributeGenericResponseDto productAttributeGeneric = productAttributeGenericService.getProductAttributeGenericById((id));
            return new ResponseEntity<>(productAttributeGeneric, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductAttributeGenericResponseDto>> getAllProductAttributesGeneric() {
        List<ProductAttributeGenericResponseDto> productAttributes = productAttributeGenericService.getAllProductAttributeGeneric();
        return new ResponseEntity<>(productAttributes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductAttributeGenericResponseDto> createProductAttributeGeneric(@RequestBody ProductAttributeGenericCreateDto productAttributeGenericCreateDto) {
        ProductAttributeGenericResponseDto productAttributeGenericResponseDto = productAttributeGenericService.createProductAttributeGeneric(productAttributeGenericCreateDto);
        return new ResponseEntity<>(productAttributeGenericResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductAttributeGenericResponseDto> editProductAttributeGeneric(@PathVariable Long id, @RequestBody ProductAttributeGenericEditDto productAttributeGenericEditDto) {
        try {
            ProductAttributeGenericResponseDto editedProductAttribute = productAttributeGenericService.editProductAttributeGeneric(id, productAttributeGenericEditDto);
            return new ResponseEntity<>(editedProductAttribute, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductAttributeGeneric(@PathVariable Long id) {
        try {
            productAttributeGenericService.deleteProductAttributeGeneric(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
