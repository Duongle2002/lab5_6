package com.example.lab5.service;

import com.example.lab5.model.Product;
import com.example.lab5.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);  // Gọi phương thức findAll(Pageable) từ JpaRepository
    }
    // Lấy tất cả sản phẩm
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Lưu sản phẩm
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Xóa sản phẩm
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
