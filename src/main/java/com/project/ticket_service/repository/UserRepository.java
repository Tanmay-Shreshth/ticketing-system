package com.project.ticket_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ticket_service.model.entity.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String userName);
    
}
