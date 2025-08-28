package com.example.demo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.UserToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTokenRepository;

import jakarta.validation.Valid;

import java.util.UUID;
import java.util.Optional;

@Service
public class AuthService {
    
    private final JavaMailSender mailSender;
    private final UserTokenRepository userTokenRepository;
    private Optional<UserToken> userToken;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(JavaMailSender mailSender, 
                       UserTokenRepository userTokenRepository,
                       Optional<UserToken> userToken, 
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder){

        this.mailSender = mailSender;
        this.userTokenRepository = userTokenRepository;
        this.userToken = userToken;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String link = "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ammar.ahmedbhs@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Welcome to SpringNext\nThanks for creating an account\n\nClick the link to verify your account:" + link);

        mailSender.send(message);
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        // save token in DB with userId and expiry
        userTokenRepository.save(new UserToken(token, user));
        return token;
    }

    public ResponseEntity<String> registerUser(@Valid User user) {

        try{
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(hashedPassword);
            User createdUser = userRepository.save(user);

            String email = user.getEmail();
            String token = generateVerificationToken(createdUser);
            sendVerificationEmail(email, token);

            return ResponseEntity.ok("Account refistered successfully!");
        } 
        catch (DataIntegrityViolationException ex) {
            // here we catch duplicate email (unique constraint violation)
            return ResponseEntity.badRequest().body("Email already exists. Please use a different one.");
        }
    }

    public ResponseEntity<String> verifyUser(String token) {

        
        userToken = userTokenRepository.findByToken(token);

        if (userToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        //if UserToken was defined without Optional Wrapper then we do userToken.getUser()
        //for Optional wrapper we use additional get()
        User user = userToken.get().getUser();
        user.setVerified(true);
        userRepository.save(user);

        //get() used again becuase of Optional wrapper
        userTokenRepository.delete(userToken.get());

        return ResponseEntity.ok("Email verified successfully!");
    }
}
