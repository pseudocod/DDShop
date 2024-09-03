package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Address.AddressCreateRequestDto;
import dd.projects.demo.domain.dto.User.*;
import dd.projects.demo.domain.entitiy.Address;
import dd.projects.demo.domain.entitiy.User;
import dd.projects.demo.mappers.AddressMapper;
import dd.projects.demo.mappers.OrderMapper;
import dd.projects.demo.mappers.UserMapper;
import dd.projects.demo.repository.AddressRepository;
import dd.projects.demo.repository.UserRepository;
import dd.projects.demo.utility.PasswordUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CartService cartService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, AddressMapper addressMapper, CartService cartService, EmailService emailService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @Transactional
    public UserResponseDto registerNewAccount(UserCreateRequestDto userCreateRequestDto) {
        Optional<User> existingUser = userRepository.findByEmail(userCreateRequestDto.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = UserMapper.INSTANCE.toEntity(userCreateRequestDto);
        user.setPassword(PasswordUtils.hashPassword(userCreateRequestDto.getPassword())); // Hashing password

        User savedUser = userRepository.save(user);
        cartService.createCart(user.getId());

        return UserMapper.INSTANCE.toUserResponseDto(savedUser);
    }

    public UserResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginRequestDto.getEmail());

        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        String hashedInputPassword = PasswordUtils.hashPassword(userLoginRequestDto.getPassword());

        if (!hashedInputPassword.equals(user.getPassword())) { // Comparing hashed passwords
            throw new IllegalArgumentException("Invalid password");
        }

        return UserMapper.INSTANCE.toUserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserEditRequestDto userEditRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setFirstName(userEditRequestDto.getFirstName());
        user.setLastName(userEditRequestDto.getLastName());
        user.setPhoneNumber(userEditRequestDto.getPhoneNumber());

        if (userEditRequestDto.getDefaultDeliveryAddress() != null) {
            Address deliveryAddress = addressMapper.toEntity(userEditRequestDto.getDefaultDeliveryAddress());

            Optional<Address> existingDeliveryAddress = addressRepository.findByDetails(
                    deliveryAddress.getStreetLine(),
                    deliveryAddress.getCity(),
                    deliveryAddress.getPostalCode(),
                    deliveryAddress.getCounty(),
                    deliveryAddress.getCountry()
            );

            if (existingDeliveryAddress.isPresent()) {
                user.setDefaultDeliveryAddress(existingDeliveryAddress.get());
            } else {
                Address savedDeliveryAddress = addressRepository.save(deliveryAddress);
                user.setDefaultDeliveryAddress(savedDeliveryAddress);
            }
        }

        if (userEditRequestDto.getDefaultBillingAddress() != null) {
            Address billingAddress = addressMapper.toEntity(userEditRequestDto.getDefaultBillingAddress());

            Optional<Address> existingBillingAddress = addressRepository.findByDetails(
                    billingAddress.getStreetLine(),
                    billingAddress.getCity(),
                    billingAddress.getPostalCode(),
                    billingAddress.getCounty(),
                    billingAddress.getCountry()
            );

            if (existingBillingAddress.isPresent()) {
                user.setDefaultBillingAddress(existingBillingAddress.get());
            } else {
                Address savedBillingAddress = addressRepository.save(billingAddress);
                user.setDefaultBillingAddress(savedBillingAddress);
            }
        }

        User updatedUser = userRepository.save(user);

        return UserMapper.INSTANCE.toUserResponseDto(updatedUser);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserMapper.INSTANCE.toUserResponseDto(user);
    }


    @Transactional
    public UserResponseDto changeUserPassword(Long id, UserChangePasswordDto userChangePasswordDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String hashedNewPassword = PasswordUtils.hashPassword(userChangePasswordDto.getNewPassword());
        String hashedOldPassword = PasswordUtils.hashPassword(userChangePasswordDto.getOldPassword());

        if(!hashedOldPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if(hashedOldPassword.equals(hashedNewPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        user.setPassword(hashedNewPassword);

        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserResponseDto(updatedUser);
    }

    @Transactional
    public UserResponseDto updateUserDeliveryAddress(Long id, AddressCreateRequestDto addressCreateRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Address address = addressMapper.toEntity(addressCreateRequestDto);

        Optional<Address> existingAddress = addressRepository.findByDetails(
                address.getStreetLine(),
                address.getCity(),
                address.getPostalCode(),
                address.getCounty(),
                address.getCountry()
        );

        if (existingAddress.isPresent()) {
            user.setDefaultDeliveryAddress(existingAddress.get());
        } else {
            Address savedAddress = addressRepository.save(address);
            user.setDefaultDeliveryAddress(savedAddress);
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserResponseDto(updatedUser);
    }

    @Transactional
    public UserResponseDto updateUserBillingAddress(Long id, AddressCreateRequestDto addressCreateRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Address address = addressMapper.toEntity(addressCreateRequestDto);

        Optional<Address> existingAddress = addressRepository.findByDetails(
                address.getStreetLine(),
                address.getCity(),
                address.getPostalCode(),
                address.getCounty(),
                address.getCountry()
        );

        if (existingAddress.isPresent()) {
            user.setDefaultBillingAddress(existingAddress.get());
        } else {
            Address savedAddress = addressRepository.save(address);
            user.setDefaultBillingAddress(savedAddress);
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserResponseDto(updatedUser);
    }
}
