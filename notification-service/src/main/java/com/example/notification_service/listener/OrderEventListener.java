package com.example.notification_service.listener;

import com.example.notification_service.event.OrderPlacedEvent;
import com.example.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderEventListener {

    EmailService emailService;

    @KafkaListener(topics = "order-topic", groupId = "notification-group", containerFactory = "orderPlacedEventListenerFactory")
    public void handleOrderEvent(OrderPlacedEvent event) throws Exception {
        System.out.println("📨 Nhận được event từ Kafka: " + event);
        // Thực hiện gửi email ở đây
        emailService.sendOrderEmailWithHTMLBody(event);
    }
}
