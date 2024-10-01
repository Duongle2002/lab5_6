package com.example.lab5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.lab5.model.User;
import com.example.lab5.service.UserService;

import java.util.List;

@Controller
public class UserController {

   @Autowired
   private UserService userService;

   // Constructor
   public UserController() {
   }

   // Return list of all users
   @GetMapping("/users")
   @ResponseBody
   public List<User> getUserList() {
       return userService.findAll();
   }

   // Return a specific user by ID
   @GetMapping("/users/{id}")
   public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
       User user = userService.findById(userId);
       if (user != null) {
           return ResponseEntity.ok(user);
       }
       return ResponseEntity.status(404).body(null);  // Return 404 if not found
   }

   // Delete a user by ID
   @DeleteMapping("/users/{id}")
   @ResponseBody
   public List<User> removeUserById(@PathVariable("id") Long userId) {
       userService.delete(userId);
       return userService.findAll();  // Return updated user list
   }

   // Create a new user
   @PostMapping("/user")
   public ResponseEntity<User> createUser(@RequestBody User user) {
       userService.save(user);
       return ResponseEntity.status(201).body(user);  // Return 201 Created with new user data
   }

   // Update an existing user by ID
   @PutMapping("/user/{id}")
   public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User updateUser) {
       User user = userService.findById(userId);
       if (user != null) {
           user.setName(updateUser.getName());
           user.setEmail(updateUser.getEmail());
           userService.save(user);
           return ResponseEntity.ok(user);  // Return 200 OK with updated user data
       }
       return ResponseEntity.status(404).body(null);  // Return 404 if user not found
   }
}
