package dd.projects.demo.service;

import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteCreateRequestDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteEditDto;
import dd.projects.demo.domain.dto.AttributeValueConcrete.AttributeValueConcreteResponseDto;
import dd.projects.demo.domain.entitiy.AttributeValueConcrete;
import dd.projects.demo.domain.entitiy.AttributeValueGeneric;
import dd.projects.demo.domain.entitiy.ProductAttributeGeneric;
import dd.projects.demo.mappers.AttributeValueConcreteMapper;
import dd.projects.demo.repository.AttributeValueConcreteRepository;
import dd.projects.demo.repository.AttributeValueGenericRepository;
import dd.projects.demo.repository.ProductAttributeGenericRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeValueConcreteService {
    private final AttributeValueConcreteRepository attributeValueConcreteRepository;
    private final ProductAttributeGenericRepository productAttributeGenericRepository;
    private final AttributeValueGenericRepository attributeValueGenericRepository;
    private static final Logger logger = LoggerFactory.getLogger(AttributeValueConcreteService.class);

    public AttributeValueConcreteService(AttributeValueConcreteRepository attributeValueConcreteRepository,
                                         ProductAttributeGenericRepository productAttributeGenericRepository,
                                         AttributeValueGenericRepository attributeValueGenericRepository
                                         ) {
        this.attributeValueConcreteRepository = attributeValueConcreteRepository;
        this.productAttributeGenericRepository = productAttributeGenericRepository;
        this.attributeValueGenericRepository = attributeValueGenericRepository;
    }

    public AttributeValueConcreteResponseDto getAttributeValueConcreteById(Long id) {
        AttributeValueConcrete attributeValueConcrete = attributeValueConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Concrete attribute value not found"));
        return AttributeValueConcreteMapper.INSTANCE.toResponseDto(attributeValueConcrete);
    }

    public List<AttributeValueConcreteResponseDto> getAllAttributeValuesConcrete() {
        return attributeValueConcreteRepository.findAll().stream()
                .map(AttributeValueConcreteMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public AttributeValueConcreteResponseDto createAttributeValueConcrete(AttributeValueConcreteCreateRequestDto attributeValueConcreteCreateRequestDto) {
        AttributeValueConcrete attributeValueConcrete = AttributeValueConcreteMapper.INSTANCE.toEntity(attributeValueConcreteCreateRequestDto);

        ProductAttributeGeneric attribute = productAttributeGenericRepository.findById(attributeValueConcreteCreateRequestDto.getAttributeId())
                .orElseThrow(() -> new EntityNotFoundException("ProductAttributeGeneric not found for id: " + attributeValueConcreteCreateRequestDto.getAttributeId()));

        AttributeValueGeneric value = attributeValueGenericRepository.findById(attributeValueConcreteCreateRequestDto.getValueId())
                .orElseThrow(() -> new EntityNotFoundException("AttributeValueGeneric not found for id: " + attributeValueConcreteCreateRequestDto.getValueId()));

        attributeValueConcrete.setAttribute(attribute);
        attributeValueConcrete.setValue(value);

        AttributeValueConcrete createdAttributeValueConcrete = attributeValueConcreteRepository.save(attributeValueConcrete);

        return AttributeValueConcreteMapper.INSTANCE.toResponseDto(createdAttributeValueConcrete);
    }
    @Transactional
    public AttributeValueConcreteResponseDto editAttributeValueConcrete(Long id, AttributeValueConcreteEditDto attributeValueConcreteEditDto) {
        AttributeValueConcrete existingAttributeValueConcrete = attributeValueConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AttributeValueConcrete not found for id: " + id));

        ProductAttributeGeneric attribute = productAttributeGenericRepository.findById(attributeValueConcreteEditDto.getAttributeId())
                .orElseThrow(() -> new EntityNotFoundException("ProductAttributeGeneric not found for id: " + attributeValueConcreteEditDto.getAttributeId()));

        AttributeValueGeneric value = attributeValueGenericRepository.findById(attributeValueConcreteEditDto.getValueId())
                .orElseThrow(() -> new EntityNotFoundException("AttributeValueGeneric not found for id: " + attributeValueConcreteEditDto.getValueId()));

        existingAttributeValueConcrete.setAttribute(attribute);
        existingAttributeValueConcrete.setValue(value);

        AttributeValueConcrete updatedAttributeValueConcrete = attributeValueConcreteRepository.save(existingAttributeValueConcrete);

        return AttributeValueConcreteMapper.INSTANCE.toResponseDto(updatedAttributeValueConcrete);
    }

    public void deleteAttributeValueConcrete(Long id) {
        AttributeValueConcrete existingAttributeValueConcrete = attributeValueConcreteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AttributeValueConcrete not found for id: " + id));

        attributeValueConcreteRepository.delete(existingAttributeValueConcrete);
    }
}
