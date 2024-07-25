package dd.projects.demo.service;

import dd.projects.demo.domain.dto.User.UserCreateRequestDto;
import dd.projects.demo.domain.dto.User.UserLoginRequestDto;
import dd.projects.demo.domain.dto.User.UserResponseDto;
import dd.projects.demo.domain.entitiy.User;
import dd.projects.demo.mappers.UserMapper;
import dd.projects.demo.repository.UserRepository;
import dd.projects.demo.utility.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto registerNewAccount(UserCreateRequestDto userCreateRequestDto) {
        Optional<User> existingUser = userRepository.findByEmail(userCreateRequestDto.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = UserMapper.INSTANCE.toEntity(userCreateRequestDto);
        user.setPassword(PasswordUtils.hashPassword(userCreateRequestDto.getPassword())); // Hashing password

        User savedUser = userRepository.save(user);

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
}
