package com.example.lab5.controller;
import com.example.lab5.model.Blog;
import com.example.lab5.model.Product;
import com.example.lab5.service.BlogService;
import com.example.lab5.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private final ProductService productService;
    private final BlogService blogService;

    public HomeController(ProductService productService, BlogService blogService) {
        this.productService = productService;
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Lấy danh sách tất cả sản phẩm
        List<Product> allProducts = productService.findAll();
        List<Product> limitedProducts;

        // Giới hạn danh sách sản phẩm còn 5 mục hoặc ít hơn nếu không đủ
        if (allProducts.size() >= 5) {
            limitedProducts = allProducts.subList(0, 5);
        } else {
            limitedProducts = allProducts.subList(0, allProducts.size());
        }

        // Lấy danh sách tất cả blog
        List<Blog> allBlogs = blogService.findAll();
        List<Blog> limitedBlogs;

        // Giới hạn danh sách blog còn 5 mục hoặc ít hơn nếu không đủ
        if (allBlogs.size() >= 5) {
            limitedBlogs = allBlogs.subList(0, 5);
        } else {
            limitedBlogs = allBlogs.subList(0, allBlogs.size());
        }

        // Truyền cả danh sách sản phẩm và blog vào model
        model.addAttribute("products", limitedProducts);
        model.addAttribute("blogs", limitedBlogs);

        return "home"; // Trả về template 'home.html'
    }
}
