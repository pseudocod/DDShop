package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Address.AddressCreateRequestDto;
import dd.projects.demo.domain.dto.Address.AddressEditRequestDto;
import dd.projects.demo.domain.dto.Address.AddressResponseDto;
import dd.projects.demo.domain.entitiy.Address;
import dd.projects.demo.mappers.AddressMapper;
import dd.projects.demo.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressResponseDto getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        return AddressMapper.INSTANCE.toAddressResponseDto(address);
    }

    public List<AddressResponseDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(AddressMapper.INSTANCE::toAddressResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public AddressResponseDto createAddress(AddressCreateRequestDto addressCreateRequestDto) {
        Address address = AddressMapper.INSTANCE.toEntity(addressCreateRequestDto);
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.INSTANCE.toAddressResponseDto(savedAddress);
    }

    public AddressResponseDto editAddress(Long id, AddressEditRequestDto addressEditRequestDto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        existingAddress.setStreetLine(addressEditRequestDto.getStreetLine());
        existingAddress.setPostalCode(addressEditRequestDto.getPostalCode());
        existingAddress.setCity(addressEditRequestDto.getCity());
        existingAddress.setCounty(addressEditRequestDto.getCounty());
        existingAddress.setCountry(addressEditRequestDto.getCountry());

        Address updatedAddress = addressRepository.save(existingAddress);

        return AddressMapper.INSTANCE.toAddressResponseDto(updatedAddress);
    }

    public void deleteAddress(Long id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        addressRepository.delete(existingAddress);
    }
}
