package dd.projects.demo.service;

import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericEditDto;
import dd.projects.demo.domain.dto.AttributeValueGeneric.AttributeValueGenericResponseDto;
import dd.projects.demo.domain.entitiy.AttributeValueGeneric;
import dd.projects.demo.mappers.AttributeValueGenericMapper;
import dd.projects.demo.repository.AttributeValueGenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeValueGenericService {
    private final AttributeValueGenericRepository attributeValueGenericRepository;

    public AttributeValueGenericService(AttributeValueGenericRepository attributeValueGenericRepository) {
        this.attributeValueGenericRepository = attributeValueGenericRepository;
    }

    public AttributeValueGenericResponseDto getAttributeValueGenericById(Long id){
        AttributeValueGeneric attributeValueGeneric = attributeValueGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute value not found"));
        return AttributeValueGenericMapper.INSTANCE.toResponseDto(attributeValueGeneric);
    }

    public List<AttributeValueGenericResponseDto> getAllAttributeValueGeneric() {
        return attributeValueGenericRepository.findAll().stream()
                .map(AttributeValueGenericMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public AttributeValueGenericResponseDto createAttributeValueGeneric(AttributeValueGenericCreateRequestDto dto) {
        AttributeValueGeneric attributeValueGeneric = AttributeValueGenericMapper.INSTANCE.toEntity(dto);
        AttributeValueGeneric savedAttributeValueGeneric = attributeValueGenericRepository.save(attributeValueGeneric);
        return AttributeValueGenericMapper.INSTANCE.toResponseDto(savedAttributeValueGeneric);
    }
    @Transactional
    public AttributeValueGenericResponseDto editAttributeValueGeneric(Long id, AttributeValueGenericEditDto attributeValueGenericEditDto) {
        AttributeValueGeneric existingAttributeValueGeneric = attributeValueGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingAttributeValueGeneric.setValue(attributeValueGenericEditDto.getValue());

        AttributeValueGeneric updatedAttributeValueGeneric = attributeValueGenericRepository.save(existingAttributeValueGeneric);
        return AttributeValueGenericMapper.INSTANCE.toResponseDto(updatedAttributeValueGeneric);
    }

    public void deleteAttributeValueGeneric(Long id) {
        AttributeValueGeneric attributeValueGeneric = attributeValueGenericRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute value not found"));

        attributeValueGenericRepository.delete(attributeValueGeneric);
    }
}
