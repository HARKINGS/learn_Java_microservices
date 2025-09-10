package com.example.notification_service.service;

import com.example.notification_service.event.OrderPlacedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    JavaMailSender mailSender;
//    TemplateEngine templateEngine;

    public void sendOrderEmail(OrderPlacedEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hello@demomailtrap.co");
            helper.setTo("tmngoc1108@gmail.com");
            helper.setSubject("🛒 Đơn hàng mới #" + event.getOrderId());

            String body = "<p>Xin chào userId <b>" + event.getUserId() + "</b>,</p>" +
                    "<p>Đơn hàng #" + event.getOrderId() + " vừa được tạo thành công!</p>" +
                    "<p>Tổng tiền: <b>$" + event.getTotal() + "</b></p>";

            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("📧 Gửi email thành công!");

        } catch (MessagingException e) {
            System.err.println("❌ Gửi email thất bại: " + e.getMessage());
        }
    }

    public void sendOrderEmailWithHTMLBody(OrderPlacedEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hello@demomailtrap.co");
            helper.setTo("tmngoc1108@gmail.com");
            helper.setSubject("🛒 Đơn hàng mới #" + event.getOrderId());

            try (var inputStream = Objects.requireNonNull(EmailService.class
                    .getResourceAsStream("/templates/email/template-" + event.getOption() + "/index.html"))) {
                helper.setText(
                        new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                        true
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Tự động load tất cả ảnh trong folder templates/email/images
            ClassPathResource imageFolder = new ClassPathResource("/templates/email/template-" + event.getOption() + "/images");
            File[] resources = imageFolder.getFile().listFiles() != null
                    ? imageFolder.getFile().listFiles(File::isFile)
                    : new java.io.File[0];

            for (java.io.File file : Objects.requireNonNull(resources)) {
                helper.addInline(file.getName(), new ClassPathResource("/templates/email/template-" + event.getOption() + "/images/" + file.getName()));
            }

            mailSender.send(message);
            System.out.println("📧 Gửi email thành công!");
        } catch (MessagingException | IOException e) {
            System.err.println("❌ Gửi email thất bại: " + e.getMessage());
        }
    }
}

