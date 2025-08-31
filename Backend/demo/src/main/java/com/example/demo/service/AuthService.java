package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import com.example.demo.model.Otp;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.OtpRepository;

import jakarta.validation.Valid;

import java.util.Optional;

@Service
public class AuthService {
    
    private final JavaMailSender mailSender;
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${MAIL_USERNAME}")
    private String fromEmail;  // injects your Gmail username

    public AuthService(JavaMailSender mailSender, 
                       OtpRepository otpRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder){

        this.mailSender = mailSender;
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendVerificationEmail(String toEmail, String code) {
        // String link = "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Welcome to SpringNext\nThanks for creating an account\n\nOTP to verify your account:" + code);

        mailSender.send(message);
    }

    public String generateVerificationCode(User user) {

        final SecureRandom random = new SecureRandom();

        StringBuilder otpCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otpCode.append(random.nextInt(10)); // digit between 0-9
        }

        // save token in DB with userId and expiry
        otpRepository.save(new Otp(otpCode.toString(), user));
        return otpCode.toString();
    }

    public ResponseEntity<String> loginUser(@Valid User user) {

        final User logUser = userRepository.findByEmail(user.getEmail()).get();
        if(logUser == null)
        {
            return ResponseEntity.badRequest().body("Credentaials not match");
        }
        else
        {
            final String rawPass = user.getPassword();
            final String encodedPass = logUser.getPassword();
            boolean matchPass = passwordEncoder.matches(rawPass, encodedPass);
            if(matchPass == false)
            {
                return ResponseEntity.badRequest().body("Credentaials not match");
            }
        }

        return ResponseEntity.ok("User authenticated");
    }


    public ResponseEntity<String> registerUser(@Valid User user) {

        try{
            System.out.println(user);
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(hashedPassword);
            User createdUser = userRepository.save(user);

            String email = user.getEmail();
            String code = generateVerificationCode(createdUser);
            sendVerificationEmail(email, code);

            return ResponseEntity.ok("Account registered successfully!");
        } 
        catch (DataIntegrityViolationException ex) {
            // here we catch duplicate email (unique constraint violation)
            System.out.println("here");
            return ResponseEntity.badRequest().body("Email already exists. Please use a different one.");
        }
    }

    public ResponseEntity<String> verifyUser(String code) {

        
        Optional<Otp> otp = otpRepository.findByCode(code);

        if (otp.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid OTP code");
        }

        //if UserToken was defined without Optional Wrapper then we do userToken.getUser()
        //for Optional wrapper we use additional get()
        User user = otp.get().getUser();
        user.setVerified(true);
        userRepository.save(user);

        //get() used again becuase of Optional wrapper
        otpRepository.delete(otp.get());

        return ResponseEntity.ok("Email verified successfully!");
    }
}
