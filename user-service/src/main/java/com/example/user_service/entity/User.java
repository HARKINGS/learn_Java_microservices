package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Table(name = "users") // cho ai chưa biết thì users nhớ thêm s nhé, chứ ko thêm s la thanh user keyword trong Sql và khi chay code se bi loi
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String password;
    String name;
    @Column(unique = true)
    String email;
    String keycloakId;
}
