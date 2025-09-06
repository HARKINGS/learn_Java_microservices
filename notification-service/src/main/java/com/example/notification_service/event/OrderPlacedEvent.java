package com.example.notification_service.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderPlacedEvent {
    Long orderId;
    Long userId;
    Double total;
}
