package com.example.lab5.controller;
 // Change exception name for clarity
import com.example.lab5.exception.ProductNotFoundException;
import com.example.lab5.model.Blog;
import com.example.lab5.repository.BlogRepository;
import com.example.lab5.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogSerice;

    // API for getting paginated blogs
    @GetMapping("/blogs/api")
    @ResponseBody
    public Page<Blog> getBlogsApi(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogRepository.findAll(pageable);
    }

    // Thymeleaf View for getting paginated blogs
    @GetMapping("/blogs/all")
    public String getBlogsForView(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 10;  // You can make this dynamic if needed
        Page<Blog> blogPage = blogSerice.findAll(PageRequest.of(page, size));

        model.addAttribute("blogs", blogPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());

        // Safety check for page values
        if (page < 0) {
            model.addAttribute("errorMessage", "Page number cannot be less than 0");
            return "error";
        }

        return "blog";
    }

    // Single blog view
    @GetMapping("/blogs/{id}")
    public String getBlog(@PathVariable Long id, Model model) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));  // Use Blog-specific exception
        System.out.println("Blog found: " + blog);
        model.addAttribute("blog", blog);
        return "blog_detail";
    }

    // API for returning all blogs
    @GetMapping("/api/blogs")
    @ResponseBody
    public List<Blog> getBlogList() {
        return blogRepository.findAll();
    }

    // API for getting a single blog by ID
    @GetMapping("/api/blogs/{id}")
    @ResponseBody
    public Blog getBlogById(@PathVariable Long id) {
        return blogSerice.findById(id);
    }

    // API to create a new blog
    @PostMapping("/api/blogs")
    @ResponseBody
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        blogSerice.save(blog);
        return ResponseEntity.ok().body(blog);
    }

    // API to update an existing blog
    @PutMapping("/api/blogs/{id}")
    @ResponseBody
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog updateblog) {
        Blog blog = blogSerice.findById(id);
        if (blog != null) {
            blog.setTitle(updateblog.getTitle());
            blog.setContent(updateblog.getContent());
            blogSerice.save(blog);
            return ResponseEntity.ok().body(blog);
        }
        return ResponseEntity.notFound().build();
    }

    // Custom exception handling for BlogNotFound
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleBlogNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
