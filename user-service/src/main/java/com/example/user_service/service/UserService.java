package com.example.user_service.service;

import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    @Cacheable(value = "allUsers", key = "'all'")
    public List<User> getAllUsers() {
        System.out.println("⏳ Querying DB...");
        return userRepository.findAll();
    }

    public User ensureUserExistsFromToken(Jwt jwt) {
        String keycloakId = jwt.getSubject(); // sub
        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    // Tạo mới user nếu chưa có
                    User user = new User();
                    user.setKeycloakId(keycloakId);
                    user.setEmail(jwt.getClaim("email"));
                    user.setName(jwt.getClaim("preferred_username"));
                    return userRepository.save(user);
                });
    }
}
