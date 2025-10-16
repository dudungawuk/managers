package com.nabil.managers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nabil.managers.entity.User;


public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
} 