//package com.example.user_service.controller;
//
//import com.example.user_service.entity.User;
//import com.example.user_service.repository.UserRepository;
//import com.example.user_service.service.JwtService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class AuthController {
//
//    UserRepository userRepo;
//    JwtService jwtService;
//    BCryptPasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
//        String email = body.get("email");
//        String password = body.get("password");
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
//        }
//
//        String token = jwtService.generateToken(user);
//        return ResponseEntity.ok(Map.of("token", token));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
//        String email = body.get("email");
//        String password = body.get("password");
//        String name = body.get("name");
//
//        if (userRepo.findByEmail(email).isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
//        }
//
//        User newUser = new User();
//        newUser.setEmail(email);
//        newUser.setPassword(passwordEncoder.encode(password));
//        newUser.setName(name);
//
//        userRepo.save(newUser);
//
//        String token = jwtService.generateToken(newUser);
//        return ResponseEntity.ok(Map.of("token", token));
//    }
//}