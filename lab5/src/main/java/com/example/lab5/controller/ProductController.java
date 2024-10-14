package com.example.lab5.controller;

import com.example.lab5.exception.ProductNotFoundException;
import com.example.lab5.model.Product;
import com.example.lab5.repository.ProductRepository;
import com.example.lab5.service.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);  // Tạo đối tượng Pageable
        return productService.findAll(pageable);  // Trả về dữ liệu phân trang
    }
    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));
        System.out.println("Product found: " + product);
        model.addAttribute("product", product);
        return "product_detail";
    }



    // Trả về trang HTML hiển thị danh sách sản phẩm
    @GetMapping("/shop")
    public String getProductList(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 10; // Số sản phẩm trên mỗi trang
        Page<Product> productPage = productService.findAll(PageRequest.of(page, size));

        model.addAttribute("products", productPage.getContent()); // Lấy danh sách sản phẩm
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "shop";  // Trả về file shop.html
    }


    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProductList() {
        return productService.findAll();  // Trả về danh sách sản phẩm dưới dạng JSON
    }
    @GetMapping("/api/products/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }


    @PostMapping("/api/products")
    @ResponseBody
    public ResponseEntity <Product> createProduct(@RequestBody Product product){
        productService.save(product);
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/api/products/{id}")
    @ResponseBody
    public ResponseEntity <Product> updateProduct (@PathVariable ("id") Long productId , @RequestBody Product updateProduct){
        Product product = productService.findById(productId);
        if (product != null) {
            product.setName(updateProduct.getName());
            product.setBrand(updateProduct.getBrand());
            product.setCategory(updateProduct.getCategory());
            product.setImageFilename(updateProduct.getImageFilename());
            product.setPrice(updateProduct.getPrice());
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(404).body(null);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";  // Chuyển đến trang lỗi tùy chỉnh
    }
}
