package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Product.ProductCreateRequestDto;
import dd.projects.demo.domain.dto.Product.ProductEditRequestDto;
import dd.projects.demo.domain.dto.Product.ProductResponseDto;
import dd.projects.demo.domain.dto.ProductAttribute.ProductAttributeCreateRequestDto;
import dd.projects.demo.domain.entitiy.*;
import dd.projects.demo.mappers.ProductMapper;
import dd.projects.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ProductAttributeConcreteRepository productAttributeConcreteRepository;
    private AttributeValueConcreteRepository attributeValueConcreteRepository;
    private ProductAttributeGenericRepository productAttributeGenericRepository;
    private AttributeValueGenericRepository attributeValueGenericRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductAttributeConcreteRepository productAttributeConcreteRepository, AttributeValueConcreteRepository attributeValueConcreteRepository, ProductAttributeGenericRepository productAttributeGenericRepository, AttributeValueGenericRepository attributeValueGenericRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productAttributeConcreteRepository = productAttributeConcreteRepository;
        this.attributeValueConcreteRepository = attributeValueConcreteRepository;
        this.productAttributeGenericRepository = productAttributeGenericRepository;
        this.attributeValueGenericRepository = attributeValueGenericRepository;
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
    public List<ProductResponseDto> getFirst3Products() {
        List<Product> products = productRepository.findFirst3Products();
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }
    public List<ProductResponseDto> getProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findProductByCategoryId(categoryId);
        return products.stream()
                .map(ProductMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    //CREATE PRODUCT
    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto request) {
        Product savedProduct = saveInitialProduct(request);

        List<ProductAttributeCreateRequestDto> requestAttributes = request.getAttributes();
        if (requestAttributes != null && !requestAttributes.isEmpty()) {
            processAttributes(requestAttributes, savedProduct);
        }

        savedProduct = productRepository.save(savedProduct);

        return ProductMapper.INSTANCE.toResponseDto(savedProduct);
    }

    private void processAttributes(List<ProductAttributeCreateRequestDto> requestAttributes, Product savedProduct) {
        for (ProductAttributeCreateRequestDto requestAttribute : requestAttributes) {
            Long attributeId = requestAttribute.getAttributeId();
            Long valueId = requestAttribute.getValueId();

            ProductAttributeGeneric productAttributeGeneric = getProductAttributeGeneric(attributeId);
            AttributeValueGeneric attributeValueGeneric = getAttributeValueGeneric(valueId);

            AttributeValueConcrete valueConcrete = findOrCreateAttributeValueConcrete(productAttributeGeneric, attributeValueGeneric);

            createProductAttributeConcrete(savedProduct, valueConcrete);
        }
    }
    private ProductAttributeGeneric getProductAttributeGeneric(Long attributeId) {
        return productAttributeGenericRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product attribute generic."));
    }
    private AttributeValueGeneric getAttributeValueGeneric(Long valueId) {
        return attributeValueGenericRepository.findById(valueId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find attribute generic value."));
    }
    private AttributeValueConcrete findOrCreateAttributeValueConcrete(ProductAttributeGeneric productAttributeGeneric, AttributeValueGeneric attributeValueGeneric) {
        return attributeValueConcreteRepository.findByAttributeAndValue(productAttributeGeneric, attributeValueGeneric)
                .stream()
                .findFirst()
                .orElseGet(() -> createAttributeValueConcrete(productAttributeGeneric, attributeValueGeneric));
    }
    private AttributeValueConcrete createAttributeValueConcrete(ProductAttributeGeneric productAttributeGeneric, AttributeValueGeneric attributeValueGeneric) {
        AttributeValueConcrete newValueConcrete = new AttributeValueConcrete();
        newValueConcrete.setAttribute(productAttributeGeneric);
        newValueConcrete.setValue(attributeValueGeneric);
        return attributeValueConcreteRepository.save(newValueConcrete);
    }
    private void createProductAttributeConcrete(Product savedProduct, AttributeValueConcrete valueConcrete) {
        ProductAttributeConcrete productAttributeConcrete = new ProductAttributeConcrete();
        productAttributeConcrete.setProduct(savedProduct);
        productAttributeConcrete.setAttributeValueConcrete(valueConcrete);

        if (savedProduct.getProductAttributes() == null) {
            savedProduct.setProductAttributes(new ArrayList<>());
        }

        savedProduct.getProductAttributes().add(productAttributeConcrete);
        productAttributeConcreteRepository.save(productAttributeConcrete);
    }
    private Product saveInitialProduct(ProductCreateRequestDto request) {
        Product product = ProductMapper.INSTANCE.toEntity(request);
        product.setAddedDate(LocalDate.now());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id " + request.getCategoryId()));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    //EDIT PRODUCT
    @Transactional
    public ProductResponseDto editProduct(Long id, ProductEditRequestDto productEditRequestDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id: " + id));

        Product updateBasicDetailsProduct = updateBasicDetails(productEditRequestDto, existingProduct);

        List<ProductAttributeConcrete> existingAttributes = updateBasicDetailsProduct.getProductAttributes();
        List<ProductAttributeCreateRequestDto> newAttributes = productEditRequestDto.getAttributes();

        if (newAttributes == null) {
            newAttributes = new ArrayList<>();
        }
        final List<ProductAttributeCreateRequestDto> finalNewAttributes = newAttributes;

        for (ProductAttributeCreateRequestDto newAttribute : finalNewAttributes) {
            boolean exists = existingAttributes.stream().anyMatch(existingAttr ->
                    existingAttr.getAttributeValueConcrete().getAttribute().getId().equals(newAttribute.getAttributeId()) &&
                            existingAttr.getAttributeValueConcrete().getValue().getId().equals(newAttribute.getValueId()));

            if (!exists) {
                // Add new attribute
                ProductAttributeGeneric attributeGeneric = getProductAttributeGeneric(newAttribute.getAttributeId());
                AttributeValueGeneric valueGeneric = getAttributeValueGeneric(newAttribute.getValueId());
                AttributeValueConcrete valueConcrete = findOrCreateAttributeValueConcrete(attributeGeneric, valueGeneric);
                createProductAttributeConcrete(updateBasicDetailsProduct, valueConcrete);
            }
        }
        List<ProductAttributeConcrete> attributesToRemove = existingAttributes.stream()
                .filter(existingAttr -> finalNewAttributes.stream().noneMatch(newAttr ->
                        newAttr.getAttributeId().equals(existingAttr.getAttributeValueConcrete().getAttribute().getId()) &&
                                newAttr.getValueId().equals(existingAttr.getAttributeValueConcrete().getValue().getId())))
                .collect(Collectors.toList());

        for (ProductAttributeConcrete attributeToRemove : attributesToRemove) {
            updateBasicDetailsProduct.getProductAttributes().remove(attributeToRemove);
            productAttributeConcreteRepository.delete(attributeToRemove);
        }
        // Save and return the updated product
        Product updatedProduct = productRepository.save(updateBasicDetailsProduct);
        return ProductMapper.INSTANCE.toResponseDto(updatedProduct);
    }

    private Product updateBasicDetails(ProductEditRequestDto productEditRequestDto, Product existingProduct) {
        existingProduct.setName(productEditRequestDto.getName());
        existingProduct.setDescription(productEditRequestDto.getDescription());
        existingProduct.setPrice(existingProduct.getPrice());
        existingProduct.setAvailableQuantity(productEditRequestDto.getAvailableQuantity());

        Category category = categoryRepository.findById(productEditRequestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id: " + productEditRequestDto.getCategoryId()));
        existingProduct.setCategory(category);

        return existingProduct;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product with id " + id));
        productRepository.delete(product);
    }
}
