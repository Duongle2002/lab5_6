package com.example.lab5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lab5.model.User;
import com.example.lab5.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
   @Autowired
   private UserRepository userRepository;


   public List<User> findAll() {
       return userRepository.findAll();
   }


   public User findById(Long id) {
       return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
   }


   public User save(User user) {
       return userRepository.save(user);
   }


   public User update(User user) {
       return userRepository.save(user);
   }
   public void delete(Long id) {
       userRepository.deleteById(id);
   }
}
