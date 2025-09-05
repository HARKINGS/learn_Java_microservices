package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // cho ai chưa biết thì users nhớ thêm s nhé, chứ ko thêm s la thanh user keyword trong Sql và khi chay code se bi loi
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
}
