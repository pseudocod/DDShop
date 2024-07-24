package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.User.UserCreateRequestDto;
import dd.projects.demo.domain.dto.User.UserLoginRequestDto;
import dd.projects.demo.domain.dto.User.UserResponseDto;
import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUserAccount(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        try {
            UserResponseDto user = userService.registerNewAccount(userCreateRequestDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            UserResponseDto userResponseDto = userService.login(userLoginRequestDto);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
