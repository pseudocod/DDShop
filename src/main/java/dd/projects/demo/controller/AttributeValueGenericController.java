package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericEditDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericResponseDto;
import dd.projects.demo.service.AttributeValueGenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attribute-values-generic")
public class AttributeValueGenericController {
    private final AttributeValueGenericService attributeValueGenericService;

    public AttributeValueGenericController(AttributeValueGenericService attributeValueGenericService) {
        this.attributeValueGenericService = attributeValueGenericService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeValueGenericResponseDto> getAttributeValueGenericById(@PathVariable Long id) {
        try {
            AttributeValueGenericResponseDto attributeValueGeneric = attributeValueGenericService.getAttributeValueGenericById(id);
            return new ResponseEntity<>(attributeValueGeneric, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AttributeValueGenericResponseDto>> getAllAttributeValuesGeneric() {
        List<AttributeValueGenericResponseDto> attributeValueGeneric = attributeValueGenericService.getAllAttributeValueGeneric();
        return new ResponseEntity<>(attributeValueGeneric, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AttributeValueGenericResponseDto> createAttributeValueGeneric(@RequestBody AttributeValueGenericCreateRequestDto attributeValueGenericCreateRequestDto) {
        AttributeValueGenericResponseDto attributeValueGeneric = attributeValueGenericService.createAttributeValueGeneric(attributeValueGenericCreateRequestDto);
        return new ResponseEntity<>(attributeValueGeneric, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AttributeValueGenericResponseDto> updateAttributeValueGeneric(@PathVariable Long id, @RequestBody AttributeValueGenericEditDto attributeValueGenericEditDto) {
        try {
            AttributeValueGenericResponseDto updatedAttributeValueGenericResponseDto = attributeValueGenericService.editAttributeValueGeneric(id, attributeValueGenericEditDto);
            return new ResponseEntity<>(updatedAttributeValueGenericResponseDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttributeValueGeneric(@PathVariable Long id) {
        try {
            attributeValueGenericService.deleteAttributeValueGeneric(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
