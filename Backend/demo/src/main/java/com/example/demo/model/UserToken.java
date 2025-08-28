package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Token is required")
    @Column(nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name="user_id", nullable= false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public UserToken(String token, User user ){
        this.user = user;
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }
}
