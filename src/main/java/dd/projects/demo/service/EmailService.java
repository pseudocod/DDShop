package dd.projects.demo.service;

import dd.projects.demo.domain.entitiy.Cart;
import dd.projects.demo.domain.entitiy.CartEntry;
import dd.projects.demo.domain.entitiy.Order;
import dd.projects.demo.domain.entitiy.User;
import dd.projects.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final UserRepository userRepository;

    public EmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendHtmlEmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, "recipient@example.com");
        message.setSubject("Test email from Spring");

        String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
                "<p>It can contain <strong>HTML</strong> content.</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public void sendOrderConfirmationEmail(User user, Order order, Cart savedOrderedCart) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("support@oricând.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Order Confirmation - Order #" + order.getId());

        String htmlTemplate = readFile("C:\\Users\\mocan\\OneDrive\\Desktop\\DDShop\\demo\\src\\main\\resources\\templates\\templateForSendingOrderConfirmation.html");

        htmlTemplate = htmlTemplate.replace("${name}", user.getFirstName() + ' ' + user.getLastName());
        htmlTemplate = htmlTemplate.replace("${orderNumber}", order.getId().toString());
        htmlTemplate = htmlTemplate.replace("${orderDate}", order.getOrderDate().toString());

        StringBuilder cartEntriesHtml = new StringBuilder();
        for (CartEntry entry : savedOrderedCart.getCartEntries()) {
            cartEntriesHtml.append("<tr>")
                    .append("<td>").append(entry.getProduct().getName()).append("</td>")
                    .append("<td>").append(entry.getQuantity()).append("</td>")
                    .append("<td>").append(entry.getTotalPriceEntry()).append("</td>")
                    .append("</tr>");
        }
        htmlTemplate = htmlTemplate.replace("${cartEntries}", cartEntriesHtml.toString());

        htmlTemplate = htmlTemplate.replace("${totalPrice}", order.getTotalPrice().toString());

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }


    public void sendUserChangePasswordConfirmation(Long id) throws MessagingException, IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("support@oricând.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Change Password - " + user.getFirstName() + ' ' + user.getLastName());

        String htmlTemplate = readFile("C:\\Users\\mocan\\OneDrive\\Desktop\\DDShop\\demo\\src\\main\\resources\\templates\\templateForSendingChangePasswordMail.html");

        htmlTemplate = htmlTemplate.replace("${name}", user.getFirstName() + ' ' + user.getLastName());
        htmlTemplate = htmlTemplate.replace("${resetLink}", "http://localhost:3000/change-password");

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public void sendForgotPasswordConfirmation(String email) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("support@oricând.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Change Password for " + email);;

        String htmlTemplate = readFile("C:\\Users\\mocan\\OneDrive\\Desktop\\DDShop\\demo\\src\\main\\resources\\templates\\templateForSendingForgotPasswordMail.html");

        htmlTemplate = htmlTemplate.replace("${resetLink}", "http://localhost:3000/forgot-password");

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }


    public String readFile(String filePath) throws IOException {
      Path path = Paths.get(filePath);
      return Files.readString(path, StandardCharsets.UTF_8);
    }
}
