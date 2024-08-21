package dd.projects.demo.service;

import dd.projects.demo.domain.entitiy.Order;
import dd.projects.demo.domain.entitiy.User;
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

    public void sendOrderConfirmationEmail(User user, Order order) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("support@oricând.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Order Confirmation - Order #" + order.getId());

        String htmlTemplate = readFile("C:\\Users\\mocan\\OneDrive\\Desktop\\DDShop\\demo\\src\\main\\resources\\templates\\templateForSendingOrderConfirmation.html");

        htmlTemplate = htmlTemplate.replace("${name}", user.getFirstName() + ' ' + user.getLastName());
        htmlTemplate = htmlTemplate.replace("${orderNumber}", order.getId().toString());
        htmlTemplate = htmlTemplate.replace("${orderDate}", order.getOrderDate().toString());
        htmlTemplate = htmlTemplate.replace("${totalPrice}", order.getTotalPrice().toString());


        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public String readFile(String filePath) throws IOException {
      Path path = Paths.get(filePath);
      return Files.readString(path, StandardCharsets.UTF_8);
    }
}
