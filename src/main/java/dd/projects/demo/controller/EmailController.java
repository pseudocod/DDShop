package dd.projects.demo.controller;

import dd.projects.demo.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/password-reset/{id}")
    public ResponseEntity<String> resetPassword(@PathVariable Long id) {
        try {
            emailService.sendUserChangePasswordConfirmation(id);
            return new ResponseEntity<>("Email sent", HttpStatus.OK);
        } catch (IllegalArgumentException | MessagingException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> resetPassword(@RequestBody String email) {
        try {
            emailService.sendForgotPasswordConfirmation(email);
            return new ResponseEntity<>("Email sent", HttpStatus.OK);
        } catch (IllegalArgumentException | MessagingException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
