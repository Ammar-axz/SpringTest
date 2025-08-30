package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "User Name is required")
    @Column(nullable = false)
    private String userName;

    @NotBlank(message = "First Name is required")
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column( length = 20, unique = true)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isVerified;

    @OneToOne(mappedBy = "user")
    private Otp otp;
}
