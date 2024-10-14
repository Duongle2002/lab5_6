package com.example.lab5.service;

import com.example.lab5.model.Blog;
import com.example.lab5.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    // Method to find all blogs with pagination
    public Page<Blog> findAll(PageRequest pageRequest) {
        return blogRepository.findAll(pageRequest);
    }

    // Method to find a blog by its ID
    public Blog findById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    // Method to save a new blog
    @Transactional
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    // Method to delete a blog
    @Transactional
    public void delete(Blog blog) {
        blogRepository.delete(blog);
    }
}
