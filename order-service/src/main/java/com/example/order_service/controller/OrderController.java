package com.example.order_service.controller;

import com.example.order_service.UserClient;
import com.example.order_service.dto.request.UserDto;
import com.example.order_service.dto.response.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    OrderRepository orderRepository;
    UserClient userClient;

    @PostMapping("/prototype")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order, JwtAuthenticationToken auth) {
        String sub = auth.getToken().getSubject(); // lấy Keycloak-sub (UUID)
        UserDto user = userClient.getUserByKeycloakId(sub); // Feign gọi user-service

        order.setUserId(user.getId()); // gán Long userId
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

//        Gọi user-service = Feign
        UserDto userDto = userClient.getUserById(order.getUserId());

        return OrderResponse.builder()
                .orderId(order.getId())
                .product(order.getProduct())
                .price(order.getTotal())
                .user(userDto)
                .build();
    }
}