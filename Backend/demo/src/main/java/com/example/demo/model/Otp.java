package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Code is required")
    @Column(nullable = false)
    private String code;

    @OneToOne
    @JoinColumn(name="user_id", nullable= false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public Otp(String code, User user ){
        this.user = user;
        this.code = code;
        this.expiryDate = LocalDateTime.now().plusMinutes(5);
    }
}
