package com.example.order_service.dto.response;

import com.example.order_service.dto.request.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long orderId;
    String product;
    Double price;
    UserDto user;
}
