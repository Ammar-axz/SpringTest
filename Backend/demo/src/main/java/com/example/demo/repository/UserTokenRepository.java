package com.example.demo.repository;

import com.example.demo.model.UserToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTokenRepository extends JpaRepository<UserToken, Long>{

    Optional<UserToken> findByToken(String token);    
}