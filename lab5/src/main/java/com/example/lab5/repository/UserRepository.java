package com.example.lab5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab5.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
