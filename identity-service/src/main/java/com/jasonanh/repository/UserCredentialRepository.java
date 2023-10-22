package com.jasonanh.repository;

import com.jasonanh.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);
    UserCredential findUserCredentialByName(String username);
}
