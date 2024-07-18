package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteEditDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteResponseDto;
import dd.projects.demo.service.AttributeValueConcreteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attribute-values-concrete")
public class AttributeValueConcreteController {
    private final AttributeValueConcreteService attributeValueConcreteService;

    public AttributeValueConcreteController(AttributeValueConcreteService attributeValueConcreteService) {
        this.attributeValueConcreteService = attributeValueConcreteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeValueConcreteResponseDto> getAttributeValueConcreteById(@PathVariable Long id) {
        try {
            AttributeValueConcreteResponseDto attributeValueConcreteResponseDto = attributeValueConcreteService.getAttributeValueConcreteById(id);
            return new ResponseEntity<>(attributeValueConcreteResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AttributeValueConcreteResponseDto>> getAllAttributeValuesConcrete() {
        List<AttributeValueConcreteResponseDto> attributeValuesConcrete = attributeValueConcreteService.getAllAttributeValuesConcrete();
        return new ResponseEntity<>(attributeValuesConcrete, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AttributeValueConcreteResponseDto> createAttributeValuesConcrete(@RequestBody AttributeValueConcreteCreateRequestDto attributeValueConcreteCreateRequestDto) {
        AttributeValueConcreteResponseDto createdAttributeValueConcrete = attributeValueConcreteService.createAttributeValueConcrete(attributeValueConcreteCreateRequestDto);
        return new ResponseEntity<>(createdAttributeValueConcrete, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AttributeValueConcreteResponseDto> editAttributeValuesConcrete(@PathVariable Long id, @RequestBody AttributeValueConcreteEditDto attributeValueConcreteEditDto) {
        try {
            AttributeValueConcreteResponseDto attributeValueConcreteResponseDto = attributeValueConcreteService.editAttributeValueConcrete(id, attributeValueConcreteEditDto);
            return new ResponseEntity<>(attributeValueConcreteResponseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttributeValuesConcrete(@PathVariable Long id) {
        try {
            attributeValueConcreteService.deleteAttributeValueConcrete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
