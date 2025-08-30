package com.example.demo.repository;

import com.example.demo.model.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OtpRepository extends JpaRepository<Otp, Long>{

    Optional<Otp> findByCode(String code);    
}